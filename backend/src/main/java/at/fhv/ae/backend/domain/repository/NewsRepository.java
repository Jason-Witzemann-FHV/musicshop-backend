package at.fhv.ae.backend.domain.repository;

import at.fhv.ae.backend.domain.model.news.News;
import at.fhv.ae.backend.domain.model.user.User;

import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.function.Consumer;

@Local
public interface NewsRepository {

    void put(String id, News news);

    void addConsumer(User user, Consumer<News> consumer);
}
