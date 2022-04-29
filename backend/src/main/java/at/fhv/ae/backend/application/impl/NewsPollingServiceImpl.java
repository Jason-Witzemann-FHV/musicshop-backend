package at.fhv.ae.backend.application.impl;

import at.fhv.ae.backend.application.NewsPollingService;
import at.fhv.ae.backend.application.dto.NewsDTO;
import at.fhv.ae.backend.domain.model.user.User;
import at.fhv.ae.backend.domain.model.user.UserId;
import at.fhv.ae.backend.domain.repository.NewsRepository;
import at.fhv.ae.backend.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Stateless
public class NewsPollingServiceImpl implements NewsPollingService {

    @EJB
    private NewsRepository newsRepository;

    @EJB
    private UserRepository userRepository;

    @Override
    public List<NewsDTO> pollForNewNews(String userId, long lastReceivedTimeStamp) {
        User user = userRepository.userById(new UserId(userId)).orElseThrow(() -> new IllegalArgumentException("user with id " + userId + " was not found!"));
        return newsRepository.pollNews(user, lastReceivedTimeStamp)
                .stream()
                .map(news -> new NewsDTO(
                        news.title(),
                        news.body(),
                        news.dateOfEvent(),
                        news.topic(),
                        news.publishedTimeStamp()
                ))
                .collect(Collectors.toList());
    }
}
