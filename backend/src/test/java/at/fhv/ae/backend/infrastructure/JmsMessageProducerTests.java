package at.fhv.ae.backend.infrastructure;

import at.fhv.ae.backend.ServiceRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.jms.JMSException;
import javax.naming.NamingException;
import java.time.Duration;
import java.time.LocalDateTime;

public class JmsMessageProducerTests {

    private JmsMessageProducer jmsMessageProducer;

    @BeforeEach
    void setUp() {

        jmsMessageProducer = ServiceRegistry.jmsMessageProducer();
    }

    @Test
    void produceTestMessage() throws JMSException, NamingException {

        jmsMessageProducer.produce("System", "test", "test", LocalDateTime.now().plus(Duration.ofDays(1)));
    }
}
