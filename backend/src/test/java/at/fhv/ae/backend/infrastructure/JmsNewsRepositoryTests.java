package at.fhv.ae.backend.infrastructure;

import at.fhv.ae.backend.ServiceRegistry;
import at.fhv.ae.backend.domain.model.news.News;
import at.fhv.ae.backend.domain.model.user.SubscriptionTopics;
import at.fhv.ae.backend.domain.model.user.User;
import at.fhv.ae.backend.domain.model.user.UserId;
import at.fhv.ae.backend.domain.repository.NewsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.function.Consumer;

public class JmsNewsRepositoryTests {

    private NewsRepository newsRepository;

    @BeforeEach
    void setUp() {

        newsRepository = ServiceRegistry.newsRepository();
    }

    @Test
    void given_listener_when_putNews_then_callListener() {

        Consumer<News> listener = Mockito.mock(Consumer.class);

        var news = new News(
                "SystemTopic",
                "test",
                "test",
                LocalDateTime.now().plus(Duration.ofDays(1)));

        var user = new User(
                new UserId("TEST"),
                null,
                Set.of(SubscriptionTopics.POP_TOPIC, SubscriptionTopics.ROCK_TOPIC, SubscriptionTopics.SYSTEM_TOPIC)
        );

        newsRepository.addConsumer(user, listener);

        newsRepository.put("TEST", news);

        Mockito.verify(listener, Mockito.timeout(3000)).accept(news);
    }
}
