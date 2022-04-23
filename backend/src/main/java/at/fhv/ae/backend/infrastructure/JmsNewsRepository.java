package at.fhv.ae.backend.infrastructure;

import at.fhv.ae.backend.domain.model.news.News;
import at.fhv.ae.backend.domain.repository.NewsRepository;
import lombok.SneakyThrows;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.NamingException;
import java.time.*;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class JmsNewsRepository implements NewsRepository {

    private static final String TITLE_PROP = "title";
    private static final String EXPIRATION_PROP = "expiration";

    private final Context context;
    private final Session session;

    private final Set<MessageConsumer> jmsConsumers;
    private final Set<Consumer<News>> subscribers;

    public JmsNewsRepository(Context context, ConnectionFactory cf, Set<String> topics) throws JMSException, NamingException {

        this.jmsConsumers = new HashSet<>();
        this.subscribers = new HashSet<>();

        this.context = context;

        var connection = cf.createConnection();

        this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        for (String t : topics) {
            var consumer = this.session.createConsumer((Destination) context.lookup(t));
            consumer.setMessageListener(this::onMessage);
            jmsConsumers.add(consumer);
        }

        connection.start();
    }

    @SneakyThrows
    private void onMessage(Message m) {
        if (!(m instanceof TextMessage) || !(m.getJMSDestination() instanceof Topic))
            return;

        TextMessage message = (TextMessage) m;
        Topic topic = (Topic) m.getJMSDestination();

        var news = new News(
                topic.getTopicName(),
                message.getStringProperty(TITLE_PROP),
                message.getText(),
                LocalDateTime.parse(message.getStringProperty(EXPIRATION_PROP)));

        subscribers.forEach(c -> c.accept(news));
    }

    @Override
    @SneakyThrows
    public void addConsumer(Consumer<News> consumer) {
        subscribers.add(consumer);
    }

    @Override
    @SneakyThrows
    public void put(News news) {

        var msg = session.createTextMessage(news.body());
        msg.setStringProperty(TITLE_PROP, news.title());
        msg.setStringProperty(EXPIRATION_PROP, news.expiration().toString());

        var producer = session.createProducer((Destination) context.lookup(news.topic()));
        producer.send(msg);
        producer.close();
    }
}
