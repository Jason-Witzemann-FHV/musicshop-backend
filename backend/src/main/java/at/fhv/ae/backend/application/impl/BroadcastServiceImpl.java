package at.fhv.ae.backend.application.impl;

import at.fhv.ae.backend.application.BroadcastService;
import at.fhv.ae.backend.domain.model.news.News;
import at.fhv.ae.backend.domain.repository.NewsRepository;
import lombok.AllArgsConstructor;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.time.LocalDateTime;

@AllArgsConstructor
@Stateless
public class BroadcastServiceImpl implements BroadcastService {

    @EJB
    private NewsRepository newsRepository;

    public BroadcastServiceImpl() {

    }

    @Override
    public void broadcast(String userId, String topic, String title, String message, LocalDateTime expiration) {
        newsRepository.put(userId, new News(topic, title, message, expiration));
    }
}
