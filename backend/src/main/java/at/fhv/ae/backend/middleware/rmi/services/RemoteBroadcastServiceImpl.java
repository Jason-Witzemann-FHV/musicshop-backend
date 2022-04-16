package at.fhv.ae.backend.middleware.rmi.services;

import at.fhv.ae.backend.application.BroadcastService;
import at.fhv.ae.shared.rmi.RemoteBroadcastService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;

public class RemoteBroadcastServiceImpl extends UnicastRemoteObject implements RemoteBroadcastService {

    private final BroadcastService broadcastService;

    public RemoteBroadcastServiceImpl(BroadcastService broadcastService) throws RemoteException {
        this.broadcastService = broadcastService;
    }

    @Override
    public void broadcast(String topic, String title, String message, LocalDateTime expiration) throws RemoteException {
        broadcastService.broadcast(topic, title, message, expiration);
    }
}
