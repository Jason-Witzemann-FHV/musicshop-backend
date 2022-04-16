package at.fhv.ae.backend.infrastructure;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.NamingException;
import java.time.Duration;
import java.time.LocalDateTime;

public class JmsMessageProducer {

    private final Context context;
    private final Session session;

    public JmsMessageProducer(Context context, ConnectionFactory cf) throws JMSException {

        this.context = context;
        this.session = cf.createConnection().createSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    public void produce(String destination, String title, String message, LocalDateTime expiration) throws JMSException, NamingException {

        var msg = session.createTextMessage(message);
        msg.setStringProperty("title", title);

        var producer = session.createProducer((Destination) context.lookup(destination));
        producer.setTimeToLive(Duration.between(LocalDateTime.now(), expiration).toMillis());
        producer.send(msg);
        producer.close();
    }

}
