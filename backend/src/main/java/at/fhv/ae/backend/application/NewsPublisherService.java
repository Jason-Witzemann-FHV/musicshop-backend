package at.fhv.ae.backend.application;

import at.fhv.ae.backend.application.dto.NewsDTO;

import javax.ejb.Local;
import java.util.function.Consumer;

@Local
public interface NewsPublisherService {

    void addReceiver(String id, Consumer<NewsDTO> receiver);
}
