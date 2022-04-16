package at.fhv.ae.backend.middleware.common;

import at.fhv.ae.backend.application.BasketService;
import at.fhv.ae.backend.application.BroadcastService;
import at.fhv.ae.backend.application.ReleaseSearchService;
import at.fhv.ae.backend.application.SellService;
import at.fhv.ae.shared.AuthorizationException;
import at.fhv.ae.shared.repository.CustomerRepository;

public interface Session {

    BasketService basketService() throws AuthorizationException;

    ReleaseSearchService releaseSearchService() throws AuthorizationException;

    SellService sellService() throws AuthorizationException;

    CustomerRepository customerRepository() throws AuthorizationException;

    BroadcastService broadcastService() throws AuthorizationException;

    String getUserId();

}
