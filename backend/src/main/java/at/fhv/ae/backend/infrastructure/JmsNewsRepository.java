package at.fhv.ae.backend.infrastructure;

import at.fhv.ae.backend.domain.model.news.News;
import at.fhv.ae.backend.domain.model.user.SubscriptionTopics;
import at.fhv.ae.backend.domain.model.user.User;
import at.fhv.ae.backend.domain.repository.NewsRepository;
import lombok.SneakyThrows;

import javax.jms.*;
import javax.naming.Context;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class JmsNewsRepository implements NewsRepository {

    private static final String TITLE_PROP = "title";
    private static final String EXPIRATION_PROP = "expiration";

    private final Context context;
    private final Map<String, Topic> topics;

    private final Connection connection;
    private final Map<String, Map<Topic, TopicSubscriber>> subscribers;

    public JmsNewsRepository(Context context, ConnectionFactory cf, Set<String> topics) throws JMSException {

        this.context = context;

        this.connection = cf.createConnection();

        // durable subscribers are created on a per-client basis, they can't be shared.
        // this uses one client-id globally:

        this.connection.setClientID("client");
        this.connection.start();

        this.topics = topics.stream().collect(Collectors.toMap(t -> t, this::lookupTopic));
        this.subscribers = new HashMap<>();
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
