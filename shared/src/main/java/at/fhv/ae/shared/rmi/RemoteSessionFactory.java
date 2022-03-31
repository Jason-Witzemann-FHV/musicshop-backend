package at.fhv.ae.shared.rmi;

import at.fhv.ae.shared.AuthorizationException;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteSessionFactory extends Remote {

    RemoteSession logIn(String username, String password) throws RemoteException, AuthorizationException;
}
