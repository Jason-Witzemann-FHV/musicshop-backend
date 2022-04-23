package at.fhv.ae.shared.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteNewsPublisherService extends Remote {

    void addReceiver(RemoteNewsReceiver receiver) throws RemoteException;
}
