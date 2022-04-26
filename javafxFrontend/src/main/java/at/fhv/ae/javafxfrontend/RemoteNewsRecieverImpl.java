package at.fhv.ae.javafxfrontend;

import at.fhv.ae.shared.dto.news.NewsRemoteDTO;
import at.fhv.ae.shared.rmi.RemoteNewsReceiver;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.function.Consumer;

public class RemoteNewsRecieverImpl implements RemoteNewsReceiver {

    private Consumer<NewsRemoteDTO> consumer;


       public RemoteNewsRecieverImpl(Consumer<NewsRemoteDTO> consumer) {
        this.consumer = consumer;
    }


    @Override
    public void receive(NewsRemoteDTO news) {
        consumer.accept(news);
    }
}
