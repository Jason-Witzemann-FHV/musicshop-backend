package at.fhv.ae.backend.application;

import at.fhv.ae.backend.application.impl.BroadcastServiceImpl;
import at.fhv.ae.backend.infrastructure.JmsMessageProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.jms.JMSException;
import javax.naming.NamingException;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.mockito.Mockito.mock;

public class BroadcastServiceTests {

    private BroadcastService broadcastService;
    private JmsMessageProducer jmsMessageProducer;

    @BeforeEach
    void setUp() {
        jmsMessageProducer = mock(JmsMessageProducer.class);
        broadcastService = new BroadcastServiceImpl(jmsMessageProducer);
    }

    @Test
    public void given_someMessage_when_called_then_callImpl() throws JMSException, NamingException {

        String topic = "test";
        String title = "testing 123";
        String message = "'broadcast(java.lang.String, java.lang.String, java.lang.String, " +
                "java.time.LocalDateTime)' in 'at.fhv.ae.backend.application.BroadcastService' " +
                "cannot be applied to '(java.lang.String, java.lang.String, java.lang.String)'";
        LocalDateTime expiration = LocalDateTime.now().plus(Duration.ofDays(5));

        broadcastService.broadcast(topic, title, message, expiration);

        Mockito.verify(jmsMessageProducer).produce("test", title, message, expiration);
    }

}
