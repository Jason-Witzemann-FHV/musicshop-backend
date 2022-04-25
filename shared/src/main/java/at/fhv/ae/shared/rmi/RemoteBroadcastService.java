package at.fhv.ae.shared.rmi;

import javax.ejb.Local;
import javax.ejb.Remote;
import java.rmi.RemoteException;
import java.time.LocalDateTime;

@Remote
public interface RemoteBroadcastService {

    void broadcast(String topic, String title, String message, LocalDateTime expiration);
}
