package at.fhv.ae.backend.infrastructure;

import at.fhv.ae.backend.domain.model.news.News;
import at.fhv.ae.backend.domain.model.user.SubscriptionTopics;
import at.fhv.ae.backend.domain.model.user.User;
import at.fhv.ae.backend.domain.repository.NewsRepository;
import lombok.SneakyThrows;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.ejb.Stateful;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Stateful
public class JmsNewsRepository implements NewsRepository {

    private static final String TITLE_PROP = "title";
    private static final String EXPIRATION_PROP = "expiration";

    private Context context;
    private Map<String, Topic> topics;

    private Connection connection;
    private Map<String, Map<Topic, TopicSubscriber>> subscribers;


    public JmsNewsRepository() {
        try {
            var env = new Hashtable<>(Map.of(
                    Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory",
                    Context.PROVIDER_URL, "vm://10.0.40.160",
                    "topic.SystemTopic", "SystemTopic",
                    "topic.PopTopic", "PopTopic",
                    "topic.RockTopic", "RockTopic"
            ));

            this.context = new InitialContext(env);
            this.connection = new ActiveMQConnectionFactory("tcp://10.0.40.160:61616").createConnection();
            this.subscribers = new HashMap<>();
            this.topics = Set.of("SystemTopic", "PopTopic", "RockTopic").stream().collect(Collectors.toMap(t -> t, this::lookupTopic));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @SneakyThrows
    private Topic lookupTopic(String topic) {
        return (Topic) context.lookup(topic);
    }


    // id = userId
    @Override
    @SneakyThrows
    public void addConsumer(User user, Consumer<News> handler) {

        String id = user.userId().name();
        Session sess = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

        // get the subscribers, or initialize as empty map
        Map<Topic, TopicSubscriber> subs = subscribers.computeIfAbsent(id, k -> new HashMap<>());

        for(var t: topics.entrySet()) {
            String topicName = t.getKey();
            Topic topic = t.getValue();

            // replace existing subscriber for this user if there is one, to receive all old messages again

            TopicSubscriber sub = subs.get(topic);
            if(sub != null) {
                sub.close();
                subs.values().remove(sub);
            }

            // check users subscriptions and add if subscribed
            if(user.subscribedTo(topicName)) {
                sub = sess.createDurableSubscriber(topic, id + "-" + topicName);
                subs.put(t.getValue(), sub);
                sub.setMessageListener(m -> parseMessage(m).ifPresent(handler));
            }
        }
    }

    @SneakyThrows
    private Optional<News> parseMessage(Message m) {
        if(!(m instanceof TextMessage) || !(m.getJMSDestination() instanceof Topic))
            return Optional.empty();

        var message = (TextMessage) m;
        var topic = (Topic) m.getJMSDestination();

        return Optional.of(new News(
                topic.getTopicName(),
                message.getStringProperty(TITLE_PROP),
                message.getText(),
                LocalDateTime.parse(message.getStringProperty(EXPIRATION_PROP))
        ));
    }

    @Override
    @SneakyThrows
    public void put(String id, News news) {

        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

        MessageProducer producer = session.createProducer(topics.get(news.topic()));

        TextMessage message = session.createTextMessage(news.body());
        message.setStringProperty(TITLE_PROP, news.title());
        message.setStringProperty(EXPIRATION_PROP, news.expiration().toString());

        producer.send(message);
    }

}
