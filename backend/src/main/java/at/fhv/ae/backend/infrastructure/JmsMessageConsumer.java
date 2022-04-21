package at.fhv.ae.backend.infrastructure;


import javax.jms.*;
import java.util.function.Consumer;

public class JmsMessageConsumer {

    private final Session session;

    public JmsMessageConsumer(Consumer<Message> consumer, ConnectionFactory cf) throws JMSException {

        var connection = cf.createConnection();

        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        session.setMessageListener(consumer::accept);

        connection.start();
    }
}
