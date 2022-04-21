package at.fhv.ae.shared.rmi;

import java.rmi.Remote;

public interface RemoteNewsPublisherService extends Remote {

    void addReceiver(RemoteNewsReceiver receiver);
}
