package at.fhv.ae.backend.application;

import at.fhv.ae.backend.application.dto.NewsDTO;

import javax.ejb.Local;
import java.util.List;

@Local
public interface NewsPollingService {

    List<NewsDTO> pollForNewNews(String userId, long lastReceivedTimeStamp);
}
