package at.fhv.ae.backend.middleware.common;

import at.fhv.ae.backend.application.*;
import at.fhv.ae.shared.AuthorizationException;
import at.fhv.ae.shared.repository.CustomerRepository;

import javax.ejb.Local;

@Local
public interface Session {

    BasketService basketService() throws AuthorizationException;

    ReleaseSearchService releaseSearchService() throws AuthorizationException;

    SellService sellService() throws AuthorizationException;

    CustomerRepository customerRepository() throws AuthorizationException;

    BroadcastService broadcastService() throws AuthorizationException;

    NewsPublisherService newsPublisherService() throws AuthorizationException;

    String getUserId();

}
