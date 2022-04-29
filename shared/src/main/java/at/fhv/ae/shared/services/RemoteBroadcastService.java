package at.fhv.ae.shared.services;

import javax.ejb.Remote;
import java.time.LocalDateTime;

@Remote
public interface RemoteBroadcastService {

    void broadcast(String topic, String title, String message, LocalDateTime expiration);

    void init(String id);
}
