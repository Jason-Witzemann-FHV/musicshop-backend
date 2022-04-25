package at.fhv.ae.javafxfrontend;

import at.fhv.ae.shared.dto.news.NewsRemoteDTO;
import at.fhv.ae.shared.rmi.RemoteNewsReceiver;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.function.Consumer;

public class RemoteNewsRecieverImpl extends UnicastRemoteObject implements RemoteNewsReceiver {
    private final Consumer<NewsRemoteDTO> consumer;

    public RemoteNewsRecieverImpl(Consumer<NewsRemoteDTO> consumer) throws RemoteException {
        this.consumer = consumer;
    }

    @Override
    public void receive(NewsRemoteDTO news) throws RemoteException {
        consumer.accept(news);
    }
}
