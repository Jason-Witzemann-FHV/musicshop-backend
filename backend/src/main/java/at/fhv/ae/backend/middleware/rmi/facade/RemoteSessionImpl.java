package at.fhv.ae.backend.middleware.rmi.facade;

import at.fhv.ae.backend.middleware.common.Session;
import at.fhv.ae.backend.middleware.rmi.services.*;
import at.fhv.ae.shared.AuthorizationException;
import at.fhv.ae.shared.rmi.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteSessionImpl extends UnicastRemoteObject implements RemoteSession {

    private RemoteReleaseSearchService remoteReleaseSearchService;

    private RemoteBasketService remoteBasketService;

    private RemoteSellService remoteSellService;

    private RemoteCustomerSearchService remoteCustomerSearchService;

    private RemoteBroadcastService remoteBroadcastService;

    public RemoteSessionImpl(Session session) throws RemoteException {
        super();

        try {
            remoteReleaseSearchService = new RemoteReleaseSearchServiceImpl(session.releaseSearchService());
        } catch (AuthorizationException ignored) {
        }

        try {
            remoteSellService = new RemoteSellServiceImpl(session.getUserId(), session.sellService());
        } catch (AuthorizationException ignored) {
        }

        try {
            remoteBasketService = new RemoteBasketServiceImpl(session.getUserId(), session.basketService());
        } catch (AuthorizationException ignored) {
        }

        try {
            remoteCustomerSearchService = new RemoteCustomerSearchServiceImpl(session.customerRepository());
        } catch (AuthorizationException ignored) {
        }

        try {
            remoteBroadcastService = new RemoteBroadcastServiceImpl(session.broadcastService());
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

    @Override
    public RemoteCustomerSearchService remoteCustomerSearchService() throws AuthorizationException, RemoteException {
        if (remoteCustomerSearchService == null)
            throw new AuthorizationException();

        return remoteCustomerSearchService;
    }
}
