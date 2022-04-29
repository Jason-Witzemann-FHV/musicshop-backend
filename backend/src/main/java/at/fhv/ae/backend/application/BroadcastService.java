package at.fhv.ae.backend.application;

import javax.ejb.Local;
import java.time.LocalDateTime;

@Local
public interface BroadcastService {

    void broadcast(String userId, String topic, String title, String message, LocalDateTime expiration);
}
