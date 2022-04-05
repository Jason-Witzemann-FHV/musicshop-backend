package at.fhv.ae.shared.rmi;

import at.fhv.ae.shared.AuthorizationException;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteSession extends Remote {

    RemoteBasketService remoteBasketService() throws AuthorizationException, RemoteException;

    RemoteSellService remoteSellService() throws AuthorizationException, RemoteException;

    RemoteReleaseSearchService remoteReleaseService() throws AuthorizationException, RemoteException;
}
