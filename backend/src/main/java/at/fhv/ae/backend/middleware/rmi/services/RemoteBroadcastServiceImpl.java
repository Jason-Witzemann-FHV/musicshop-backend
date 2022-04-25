package at.fhv.ae.backend.middleware.rmi.services;

import at.fhv.ae.backend.application.BroadcastService;
import at.fhv.ae.shared.rmi.RemoteBroadcastService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;

public class RemoteBroadcastServiceImpl extends UnicastRemoteObject implements RemoteBroadcastService {

    private final BroadcastService broadcastService;
    private final String userId;

    public RemoteBroadcastServiceImpl(BroadcastService broadcastService, String userId) throws RemoteException {
        this.broadcastService = broadcastService;
        this.userId = userId;
    }

    @Override
    public void broadcast(String topic, String title, String message, LocalDateTime expiration) throws RemoteException {
        broadcastService.broadcast(userId, topic, title, message, expiration);
    }
}
