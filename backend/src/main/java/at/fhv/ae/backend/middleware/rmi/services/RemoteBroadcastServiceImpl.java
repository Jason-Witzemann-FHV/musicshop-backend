package at.fhv.ae.backend.middleware.rmi.services;

import at.fhv.ae.backend.application.BroadcastService;
import at.fhv.ae.shared.rmi.RemoteBroadcastService;
import lombok.AllArgsConstructor;

import java.rmi.RemoteException;
import java.time.LocalDateTime;

@AllArgsConstructor
public class RemoteBroadcastServiceImpl implements RemoteBroadcastService {

    private BroadcastService broadcastService;

    @Override
    public void broadcast(String topic, String title, String message, LocalDateTime expiration) throws RemoteException {
        broadcastService.broadcast(topic, title, message, expiration);
    }
}
