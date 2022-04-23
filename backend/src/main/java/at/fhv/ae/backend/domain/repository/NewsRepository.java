package at.fhv.ae.backend.domain.repository;

import at.fhv.ae.backend.domain.model.news.News;

import java.util.function.Consumer;

public interface NewsRepository {

    void put(News news);

    void addConsumer(Consumer<News> consumer);
}
