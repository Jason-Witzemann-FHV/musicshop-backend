package at.fhv.ae.shared.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Optional;

public interface RemoteSessionFactory extends Remote {

    Optional<RemoteSession> logIn(String username, String password) throws RemoteException;


}
