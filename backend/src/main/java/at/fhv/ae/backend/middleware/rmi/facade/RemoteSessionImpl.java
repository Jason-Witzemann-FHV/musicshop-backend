package at.fhv.ae.backend.middleware.rmi.facade;

import at.fhv.ae.backend.middleware.common.Session;
import at.fhv.ae.backend.middleware.rmi.services.*;
import at.fhv.ae.shared.AuthorizationException;
import at.fhv.ae.shared.rmi.*;

import javax.ejb.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

@Stateful
public class RemoteSessionImpl implements RemoteSession {

    private RemoteReleaseSearchService remoteReleaseSearchService;

    private RemoteBasketService remoteBasketService;

    private RemoteSellService remoteSellService;

    private RemoteCustomerSearchService remoteCustomerSearchService;

    private RemoteBroadcastService remoteBroadcastService;

    private RemoteNewsPublisherService remoteNewsPublisherService;

    public RemoteSessionImpl() {
    }

    public void setSession(Session session) {
        //try {
        remoteReleaseSearchService = new RemoteReleaseSearchServiceImpl();
        //  } catch (AuthorizationException ignored) {
        //}

        //try {
        remoteSellService = new RemoteSellServiceImpl();
        ((RemoteSellServiceImpl) remoteSellService).setUserId(session.getUserId());
        //} catch (AuthorizationException ignored) {
        // }

        //try {
        remoteBasketService = new RemoteBasketServiceImpl();
        ((RemoteBasketServiceImpl) remoteBasketService).setUserId(session.getUserId());
        // } catch (AuthorizationException ignored) {
        //}

        try {
            remoteCustomerSearchService = new RemoteCustomerSearchServiceImpl();
            ((RemoteCustomerSearchServiceImpl) remoteCustomerSearchService).setCustomerRepository(session.customerRepository());
        } catch (AuthorizationException ignored) {
        }

        //try {
        remoteBroadcastService = new RemoteBroadcastServiceImpl();
        ((RemoteBroadcastServiceImpl) remoteBroadcastService).setUserId(session.getUserId());
        //} catch (AuthorizationException ignored) {

        //}

        remoteNewsPublisherService = new RemoteNewsPublisherServiceImpl();
        ((RemoteNewsPublisherServiceImpl) remoteNewsPublisherService ).setUserId(session.getUserId());
        //try {
        //    remoteNewsPublisherService = new RemoteNewsPublisherServiceImpl(session.newsPublisherService(), session.getUserId());
        //} catch (AuthorizationException ignored) {

        //}
    }

    @Override
    public RemoteBasketService remoteBasketService() throws AuthorizationException {
        if (remoteBasketService == null)
            throw new AuthorizationException();

        return remoteBasketService;
    }

    @Override
    public RemoteSellService remoteSellService() throws AuthorizationException {
        if (remoteSellService == null)
            throw new AuthorizationException();

        return remoteSellService;
    }

    @Override
    public RemoteReleaseSearchService remoteReleaseService() throws AuthorizationException {
        if (remoteReleaseSearchService == null)
            throw new AuthorizationException();

        return remoteReleaseSearchService;
    }

    @Override
    public RemoteCustomerSearchService remoteCustomerSearchService() throws AuthorizationException {
        if (remoteCustomerSearchService == null)
            throw new AuthorizationException();

        return remoteCustomerSearchService;
    }

    @Override
    public RemoteBroadcastService remoteBroadcastService() throws AuthorizationException {
        if (remoteBroadcastService == null)
            throw new AuthorizationException();

        return remoteBroadcastService;
    }

    @Override
    public RemoteNewsPublisherService remoteNewsPublisherService() throws AuthorizationException {
        if(remoteNewsPublisherService == null)
            throw new AuthorizationException();

        return remoteNewsPublisherService;
    }


}
