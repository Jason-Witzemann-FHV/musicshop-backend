package at.fhv.ae.backend.middleware.remoteservices;

import at.fhv.ae.backend.application.BroadcastService;
import at.fhv.ae.shared.services.RemoteBroadcastService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import java.time.LocalDateTime;

@Stateful
@AllArgsConstructor
@NoArgsConstructor
public class RemoteBroadcastServiceImpl implements RemoteBroadcastService {

    @EJB
    private BroadcastService broadcastService;

    private String userId;

    @Override
    public void init(String userId) {
        if (this.userId != null) {
            throw new IllegalStateException("Instance already initialized!");
        }
        this.userId = userId;
    }

    @Override
    public void broadcast(String topic, String title, String message, LocalDateTime expiration) {
        broadcastService.broadcast(userId, topic, title, message, expiration);
    }
}
