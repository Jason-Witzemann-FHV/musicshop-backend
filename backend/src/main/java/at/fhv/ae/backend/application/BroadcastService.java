package at.fhv.ae.backend.application;

import java.time.LocalDateTime;

public interface BroadcastService {

    void broadcast(String userId, String topic, String title, String message, LocalDateTime expiration);
}
