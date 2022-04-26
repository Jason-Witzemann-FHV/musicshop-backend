package at.fhv.ae.backend.application.impl;

import at.fhv.ae.backend.application.BroadcastService;
import at.fhv.ae.backend.domain.model.news.News;
import at.fhv.ae.backend.domain.repository.NewsRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@AllArgsConstructor
@NoArgsConstructor
@Stateless
public class BroadcastServiceImpl implements BroadcastService {

    @EJB
    private NewsRepository newsRepository;

    @Override
    public void broadcast(String userId, String topic, String title, String message, LocalDateTime expiration) {
        newsRepository.put(userId, new News(topic, title, message, expiration,LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli())); // -1 = undefined. jms changes timestamp automatically
    }
}
