package at.fhv.ae.backend.application;

import at.fhv.ae.backend.application.dto.NewsDTO;

import java.util.function.Consumer;

public interface NewsPublisherService {

    void addReceiver(Consumer<NewsDTO> receiver);
}
