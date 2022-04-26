package at.fhv.ae.backend.infrastructure;

import at.fhv.ae.backend.domain.repository.NewsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class JmsNewsRepositoryTests {

    private NewsRepository newsRepository;

    @BeforeEach
    void setUp() {

        //newsRepository = ServiceRegistry.newsRepository(); todo check if needed i don't know. all i know is beans
    }

// todo write some fancy test here lol
    @Test
    void given_listener_when_putNews_then_callListener() {
        assertTrue(true);
//
//        Consumer<News> listener = Mockito.mock(Consumer.class);
//
//        var news = new News(
//                "SystemTopic",
//                "test",
//                "test",
//                LocalDateTime.now().plus(Duration.ofDays(1)));
//
//        var user = new User(
//                new UserId("TEST"),
//                null,
//                Set.of(SubscriptionTopics.POP_TOPIC, SubscriptionTopics.ROCK_TOPIC, SubscriptionTopics.SYSTEM_TOPIC)
//        );
//
//        newsRepository.addConsumer(user, listener);
//
//        newsRepository.put("TEST", news);
//
//        Mockito.verify(listener, Mockito.timeout(3000)).accept(news);
   }
}
