package at.fhv.ae.backend.infrastructure;

import at.fhv.ae.backend.domain.model.news.News;
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
        this.connection.setClientID("client");
        this.connection.start();

        this.topics = topics.stream().collect(Collectors.toMap(t -> t, this::lookupTopic));
        this.subscribers = new HashMap<>();
    }

    @SneakyThrows
    private Topic lookupTopic(String topic) {
        return (Topic) context.lookup(topic);
    }


    @Override
    @SneakyThrows
    public void addConsumer(String id, Consumer<News> handler) {
        var sess = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        var subs = subscribers.computeIfAbsent(id, k -> new HashMap<>());
        for(var t: topics.entrySet()) {
            var sub = subs.get(t.getValue());
            if(sub != null) {
                sub.close();
            }
            sub = sess.createDurableSubscriber(t.getValue(), id + "-" + t.getKey());
            subs.put(t.getValue(), sub);
            sub.setMessageListener(m -> parseMessage(m).ifPresent(handler));
        }
    }

    @Override
    @SneakyThrows
    public void removeConsumer(String id) {
        throw null;
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

        var sess = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

        var prod = sess.createProducer(topics.get(news.topic()));

        var mess = sess.createTextMessage(news.body());
        mess.setStringProperty(TITLE_PROP, news.title());
        mess.setStringProperty(EXPIRATION_PROP, news.expiration().toString());

        prod.send(mess);
    }

}
