package at.fhv.ae.backend.domain.repository;

import at.fhv.ae.backend.domain.model.news.News;
import at.fhv.ae.backend.domain.model.user.User;

import javax.ejb.Local;
import java.util.List;

@Local
public interface NewsRepository {

    List<News> pollNews(User user, long lastReceivedTimeStamp);

    void put(String id, News news);

}
