package at.fhv.ae.backend.middleware.common;

import at.fhv.ae.backend.application.BasketService;
import at.fhv.ae.backend.application.ReleaseSearchService;
import at.fhv.ae.backend.application.SellService;
import at.fhv.ae.shared.AuthorizationException;
import at.fhv.ae.shared.repository.CustomerRepository;

public interface Session {

    BasketService basketService() throws AuthorizationException;

    ReleaseSearchService releaseSearchService() throws AuthorizationException;

    SellService sellService() throws AuthorizationException;

    CustomerRepository customerRepository() throws AuthorizationException;

    String getUserId();

}
