package at.fhv.ae.shared.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteSellService extends Remote {

    /**
     * @return true on sell success; otherwise false
     */
    boolean sellItemsInBasket() throws RemoteException;

}
