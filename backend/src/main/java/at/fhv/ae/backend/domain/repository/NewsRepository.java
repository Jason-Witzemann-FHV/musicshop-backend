package at.fhv.ae.backend.domain.repository;

import at.fhv.ae.backend.domain.model.news.News;

import java.util.function.Consumer;

public interface NewsRepository {

    void put(String id, News news);

    void addConsumer(String id, Consumer<News> consumer);
}
