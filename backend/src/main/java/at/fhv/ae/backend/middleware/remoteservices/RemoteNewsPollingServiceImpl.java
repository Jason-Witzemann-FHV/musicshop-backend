package at.fhv.ae.backend.middleware.remoteservices;

import at.fhv.ae.backend.application.NewsPollingService;
import at.fhv.ae.shared.dto.news.NewsRemoteDTO;
import at.fhv.ae.shared.services.RemoteNewsPollingService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import java.util.List;
import java.util.stream.Collectors;

@Stateful
@NoArgsConstructor
@AllArgsConstructor
public class RemoteNewsPollingServiceImpl implements RemoteNewsPollingService {

    @EJB
    private NewsPollingService newsPublisherService;

    private String userId;

    @Override
    public void init(String userId) {
        if (this.userId != null) {
            throw new IllegalStateException("Instance already initialized!");
        }
        this.userId = userId;
    }

    @Override
    public List<NewsRemoteDTO> pollForNewNews(long lastReceivedTimeStamp) {
        return newsPublisherService.pollForNewNews(userId, lastReceivedTimeStamp).stream()
                .map(news -> new NewsRemoteDTO(
                        news.title(),
                        news.message(),
                        news.dateOfEvent(),
                        news.topic(),
                        news.publishedTimeStamp()
                ))
                .collect(Collectors.toList());
    }

}
