package at.fhv.ae.backend.middleware.rmi.services;

import at.fhv.ae.backend.application.NewsPublisherService;
import at.fhv.ae.shared.dto.news.NewsRemoteDTO;
import at.fhv.ae.shared.rmi.RemoteNewsPublisherService;
import at.fhv.ae.shared.rmi.RemoteNewsReceiver;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

@Stateful
public class RemoteNewsPublisherServiceImpl implements RemoteNewsPublisherService {

    @EJB
    private NewsPublisherService newsPublisherService;
    private String userId;

    public RemoteNewsPublisherServiceImpl() {

    }

    @Override
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public RemoteNewsPublisherServiceImpl(NewsPublisherService newsPublisherService, String userId) {
        this.newsPublisherService = newsPublisherService;
        this.userId = userId;
    }

    @Override
    public void addReceiver(RemoteNewsReceiver receiver) {
        newsPublisherService.addReceiver(userId, m -> {

                receiver.receive(new NewsRemoteDTO(m.title(), m.message(), m.dateOfEvent(), m.topic()));

        });
    }
}
