package at.fhv.ae.backend.application.impl;

import at.fhv.ae.backend.application.BroadcastService;
import at.fhv.ae.backend.infrastructure.JmsMessageProducer;
import lombok.AllArgsConstructor;

import javax.jms.JMSException;
import javax.naming.NamingException;
import java.time.LocalDateTime;

@AllArgsConstructor
public class BroadcastServiceImpl implements BroadcastService {

    private final JmsMessageProducer jmsMessageProducer;

    @Override
    public void broadcast(String topic, String title, String message, LocalDateTime expiration) {
        try {
            jmsMessageProducer.produce(topic, title, message, expiration);
        } catch (JMSException e) {
            throw new IllegalStateException("JMS exception", e);
        } catch (NamingException e) {
            throw new IllegalStateException("invalid names provided", e);
        }
    }
}
