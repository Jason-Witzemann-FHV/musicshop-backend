package at.fhv.ae.shared.services;


import at.fhv.ae.shared.dto.news.NewsRemoteDTO;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface RemoteNewsPollingService {

    List<NewsRemoteDTO> pollForNewNews(long lastReceivedTimeStamp);

    void init(String id);
}
