package at.fhv.ae.backend.application.impl;

import at.fhv.ae.backend.application.NewsPublisherService;
import at.fhv.ae.backend.application.dto.NewsDTO;
import at.fhv.ae.backend.domain.model.user.User;
import at.fhv.ae.backend.domain.model.user.UserId;
import at.fhv.ae.backend.domain.repository.NewsRepository;
import at.fhv.ae.backend.domain.repository.UserRepository;
import lombok.AllArgsConstructor;

import java.util.function.Consumer;

@AllArgsConstructor
public class NewsPublisherServiceImpl implements NewsPublisherService {

    private final NewsRepository newsRepository;

    private final UserRepository userRepository;

    @Override
    public void addReceiver(String id, Consumer<NewsDTO> receiver) {
        User user = userRepository.userById(new UserId(id)).orElseThrow(() -> new IllegalArgumentException("user with id " + id + " was not found!"));

        newsRepository.addConsumer(user, n -> receiver.accept(new NewsDTO(n.title(), n.body(), n.expiration(), n.topic())));
    }
}
