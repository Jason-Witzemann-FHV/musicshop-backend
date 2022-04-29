package at.fhv.ae.backend.infrastructure.jms;

import at.fhv.ae.backend.domain.model.news.News;
import at.fhv.ae.backend.domain.model.user.SubscriptionTopics;
import at.fhv.ae.backend.domain.model.user.User;
import at.fhv.ae.backend.domain.repository.NewsRepository;
import lombok.SneakyThrows;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Singleton
@Startup
public class JmsNewsRepository implements NewsRepository {

    private static final String TITLE_PROP = "title";
    private static final String EXPIRATION_PROP = "expiration";

    private Context context;
    private Map<String, Topic> topics;
    private Connection connection;
    private List<News> messageCache = new ArrayList<>();

    @PostConstruct
    @SneakyThrows
    void init() {
        var env = new Hashtable<>(Map.of(
                Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory",
                Context.PROVIDER_URL, "vm://10.0.40.160",
                "topic.SystemTopic", SubscriptionTopics.SYSTEM_TOPIC.friendlyName(),
                "topic.PopTopic", SubscriptionTopics.POP_TOPIC.friendlyName(),
                "topic.RockTopic", SubscriptionTopics.ROCK_TOPIC.friendlyName()
        ));

        this.context = new InitialContext(env);
        this.connection = new ActiveMQConnectionFactory("tcp://10.0.40.160:61616").createConnection();
        this.connection.setClientID(InetAddress.getLocalHost().getHostAddress());
        this.connection.start();
        this.topics = Arrays.stream(SubscriptionTopics.values())
                .map(SubscriptionTopics::friendlyName)
                .collect(Collectors.toMap(t -> t, this::lookupTopic));

        Session sess = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        for (var topic : topics.values()) {
            sess.createDurableSubscriber(topic, "my_little_server_" + topic.getTopicName())
                    .setMessageListener(msg -> parseMessage(msg).ifPresent(messageCache::add));
        }
    }

    @PreDestroy
    @SneakyThrows
    private void preDestroy() {
        connection.close();
    }

    @SneakyThrows
    private Topic lookupTopic(String topic) {
        return (Topic) context.lookup(topic);
    }


    @Override
    public List<News> pollNews(User user, long lastReceivedTimeStamp) {
        return messageCache.stream()
                .filter(n -> n.publishedTimeStamp() > lastReceivedTimeStamp)
                .filter(n -> user.subscribedTo(n.topic()))
                .collect(Collectors.toList());
    }

    @SneakyThrows
    private Optional<News> parseMessage(Message m) {
        if (!(m instanceof TextMessage) || !(m.getJMSDestination() instanceof Topic))
            return Optional.empty();

        var message = (TextMessage) m;
        var topic = (Topic) m.getJMSDestination();

        return Optional.of(new News(
                topic.getTopicName(),
                message.getStringProperty(TITLE_PROP),
                message.getText(),
                LocalDateTime.parse(message.getStringProperty(EXPIRATION_PROP)),
                m.getJMSTimestamp()
        ));
    }

    @Override
    @SneakyThrows
    public void put(String id, News news) {

        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

        MessageProducer producer = session.createProducer(topics.get(news.topic()));

        TextMessage message = session.createTextMessage(news.body());
        message.setStringProperty(TITLE_PROP, news.title());
        message.setStringProperty(EXPIRATION_PROP, news.dateOfEvent().toString());

        message.setJMSTimestamp(news.publishedTimeStamp());

        producer.send(message);
    }

}
