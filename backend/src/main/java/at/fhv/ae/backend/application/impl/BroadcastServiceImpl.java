package at.fhv.ae.backend.application.impl;

import at.fhv.ae.backend.application.BroadcastService;
import at.fhv.ae.backend.domain.model.news.News;
import at.fhv.ae.backend.domain.repository.NewsRepository;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class BroadcastServiceImpl implements BroadcastService {

    private final NewsRepository newsRepository;

    @Override
    public void broadcast(String topic, String title, String message, LocalDateTime expiration) {
        newsRepository.put(new News(topic, title, message, expiration));
    }
}
