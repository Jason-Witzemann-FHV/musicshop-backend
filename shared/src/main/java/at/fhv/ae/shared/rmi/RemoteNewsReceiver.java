package at.fhv.ae.shared.rmi;

import at.fhv.ae.shared.dto.news.NewsRemoteDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteNewsReceiver extends Remote {

    void receive(NewsRemoteDTO news) throws RemoteException;
}
