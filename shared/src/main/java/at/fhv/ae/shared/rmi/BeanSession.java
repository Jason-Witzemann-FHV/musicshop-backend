package at.fhv.ae.shared.rmi;


import at.fhv.ae.shared.AuthorizationException;

import javax.ejb.Remote;

@Remote
public interface BeanSession {

    boolean authenticate(String user, String password);

    RemoteBasketService remoteBasketService() throws AuthorizationException;

    RemoteSellService remoteSellService() throws AuthorizationException;

    RemoteReleaseSearchService remoteReleaseService() throws AuthorizationException;

    RemoteCustomerSearchService remoteCustomerSearchService() throws AuthorizationException;

    RemoteBroadcastService remoteBroadcastService() throws AuthorizationException;

    RemoteNewsPublisherService remoteNewsPublisherService() throws AuthorizationException;

}
