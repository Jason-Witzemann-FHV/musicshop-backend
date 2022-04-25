package at.fhv.ae.backend.application;

import at.fhv.ae.backend.application.impl.BroadcastServiceImpl;
import at.fhv.ae.backend.domain.model.news.News;
import at.fhv.ae.backend.domain.repository.NewsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.mockito.Mockito.mock;

public class BroadcastServiceTests {

    private BroadcastService broadcastService;
    private NewsRepository newsRepository;

    @BeforeEach
    void setUp() {
        newsRepository = mock(NewsRepository.class);
        broadcastService = new BroadcastServiceImpl(newsRepository);
    }

    @Test
    public void given_someMessage_when_called_then_callImpl() {

        String topic = "test";
        String title = "testing 123";
        String message = "'broadcast(java.lang.String, java.lang.String, java.lang.String, " +
                "java.time.LocalDateTime)' in 'at.fhv.ae.backend.application.BroadcastService' " +
                "cannot be applied to '(java.lang.String, java.lang.String, java.lang.String)'";
        LocalDateTime expiration = LocalDateTime.now().plus(Duration.ofDays(5));

        broadcastService.broadcast("TEST", topic, title, message, expiration);

        Mockito.verify(newsRepository).put("TEST", new News(topic, title, message, expiration));
    }

}
