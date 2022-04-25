package at.fhv.ae.backend.application.impl;

import at.fhv.ae.backend.application.NewsPublisherService;
import at.fhv.ae.backend.application.dto.NewsDTO;
import at.fhv.ae.backend.domain.repository.NewsRepository;
import lombok.AllArgsConstructor;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.function.Consumer;

@AllArgsConstructor
@Stateless
public class NewsPublisherServiceImpl implements NewsPublisherService {

    @EJB
    private NewsRepository newsRepository;

    public NewsPublisherServiceImpl() {

    }

    @Override
    public void addReceiver(String id, Consumer<NewsDTO> receiver) {
        newsRepository.addConsumer(id, n -> receiver.accept(new NewsDTO(n.title(), n.body(), n.expiration(), n.topic())));
    }
}
