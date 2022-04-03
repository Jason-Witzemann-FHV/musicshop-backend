package at.fhv.ae.backend.middleware.rmi.facade;

import at.fhv.ae.backend.middleware.common.Session;
import at.fhv.ae.backend.middleware.rmi.services.RemoteBasketServiceImpl;
import at.fhv.ae.backend.middleware.rmi.services.RemoteReleaseSearchServiceImpl;
import at.fhv.ae.backend.middleware.rmi.services.RemoteSellServiceImpl;
import at.fhv.ae.shared.AuthorizationException;
import at.fhv.ae.shared.rmi.RemoteBasketService;
import at.fhv.ae.shared.rmi.RemoteReleaseSearchService;
import at.fhv.ae.shared.rmi.RemoteSellService;
import at.fhv.ae.shared.rmi.RemoteSession;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteSessionImpl extends UnicastRemoteObject implements RemoteSession {

    private RemoteReleaseSearchService remoteReleaseSearchService;

    private RemoteBasketService remoteBasketService;

    private RemoteSellService remoteSellService;

    public RemoteSessionImpl(Session session) throws RemoteException {
        super();

        try {
            remoteReleaseSearchService = new RemoteReleaseSearchServiceImpl(session.releaseSearchService());
        } catch (AuthorizationException ignored) {
        }

        try {
            remoteSellService = new RemoteSellServiceImpl(session.sellService());
        } catch (AuthorizationException ignored) {
        }

        try {
            remoteBasketService = new RemoteBasketServiceImpl(session.basketService());
        } catch (AuthorizationException ignored) {
        }
    }

    @Override
    public RemoteBasketService remoteBasketService() throws AuthorizationException, RemoteException {
        if (remoteBasketService == null)
            throw new AuthorizationException();

        return remoteBasketService;
    }

    @Override
    public RemoteSellService remoteSellService() throws AuthorizationException, RemoteException {
        if (remoteSellService == null)
            throw new AuthorizationException();

        return remoteSellService;
    }

    @Override
    public RemoteReleaseSearchService remoteReleaseService() throws AuthorizationException, RemoteException {
        if (remoteReleaseSearchService == null)
            throw new AuthorizationException();

        return remoteReleaseSearchService;
    }
}
