package at.fhv.ae.backend.application.impl;

import at.fhv.ae.backend.application.NewsPublisherService;
import at.fhv.ae.backend.application.dto.NewsDTO;
import at.fhv.ae.backend.domain.repository.NewsRepository;
import lombok.AllArgsConstructor;

import java.util.function.Consumer;

@AllArgsConstructor
public class NewsPublisherServiceImpl implements NewsPublisherService {

    private final NewsRepository newsRepository;

    @Override
    public void addReceiver(Consumer<NewsDTO> receiver) {
        newsRepository.addConsumer(n -> receiver.accept(new NewsDTO(n.title(), n.body(), n.expiration(), n.topic())));
    }
}
