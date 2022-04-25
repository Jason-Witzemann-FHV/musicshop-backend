package at.fhv.ae.backend.middleware.rmi.services;

import at.fhv.ae.backend.application.BroadcastService;
import at.fhv.ae.shared.rmi.RemoteBroadcastService;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;

@Stateful
public class RemoteBroadcastServiceImpl implements RemoteBroadcastService {

    @EJB
    private BroadcastService broadcastService;

    private String userId;

    public RemoteBroadcastServiceImpl(BroadcastService broadcastService, String userId) {
        this.broadcastService = broadcastService;
        this.userId = userId;
    }

    public RemoteBroadcastServiceImpl() {

    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public void broadcast(String topic, String title, String message, LocalDateTime expiration) {
        broadcastService.broadcast(userId, topic, title, message, expiration);
    }
}
