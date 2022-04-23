package at.fhv.ae.backend.middleware.rmi.services;

import at.fhv.ae.backend.application.NewsPublisherService;
import at.fhv.ae.shared.dto.news.NewsRemoteDTO;
import at.fhv.ae.shared.rmi.RemoteNewsPublisherService;
import at.fhv.ae.shared.rmi.RemoteNewsReceiver;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteNewsPublisherServiceImpl extends UnicastRemoteObject implements RemoteNewsPublisherService {

    private final NewsPublisherService newsPublisherService;

    public RemoteNewsPublisherServiceImpl(NewsPublisherService newsPublisherService) throws RemoteException {
        this.newsPublisherService = newsPublisherService;
    }

    @Override
    public void addReceiver(RemoteNewsReceiver receiver) {
        newsPublisherService.addReceiver(m -> {
            try {
                receiver.receive(new NewsRemoteDTO(m.title(), m.message(), m.dateOfEvent(), m.topic()));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
