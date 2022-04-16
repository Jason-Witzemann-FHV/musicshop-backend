package at.fhv.ae.shared.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDateTime;

public interface RemoteBroadcastService extends Remote {

    void broadcast(String topic, String title, String message, LocalDateTime expiration) throws RemoteException;
}
