package at.fhv.ae.backend.middleware.common;

import at.fhv.ae.backend.application.BasketService;
import at.fhv.ae.backend.application.ReleaseSearchService;
import at.fhv.ae.backend.application.SellService;
import at.fhv.ae.shared.AuthorizationException;

public interface Session {

    BasketService basketService() throws AuthorizationException;

    ReleaseSearchService releaseSearchService() throws AuthorizationException;

    SellService sellService() throws AuthorizationException;

    String getUserId();

}
