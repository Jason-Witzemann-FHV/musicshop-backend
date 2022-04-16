package at.fhv.ae.backend.middleware.common.impl;

import at.fhv.ae.backend.ServiceRegistry;
import at.fhv.ae.backend.application.BasketService;
import at.fhv.ae.backend.application.BroadcastService;
import at.fhv.ae.backend.application.ReleaseSearchService;
import at.fhv.ae.backend.application.SellService;
import at.fhv.ae.backend.domain.model.user.Permission;
import at.fhv.ae.backend.domain.model.user.User;
import at.fhv.ae.backend.middleware.common.Session;
import at.fhv.ae.shared.AuthorizationException;
import at.fhv.ae.shared.repository.CustomerRepository;

public class SessionImpl implements Session {

    private BasketService basketService;

    private ReleaseSearchService releaseSearchService;

    private SellService sellService;

    private CustomerRepository customerRepository;

    private BroadcastService broadcastService;

    private String userId;

    public SessionImpl(User user) {
        if (user.hasPermission(Permission.SEARCH_RELEASES))
            releaseSearchService = ServiceRegistry.releaseService();

        if (user.hasPermission(Permission.SELL_RELEASES)) {
            sellService = ServiceRegistry.sellService();
            basketService = ServiceRegistry.basketService();
            customerRepository = ServiceRegistry.customerRepository();
        }

        if(user.hasPermission(Permission.PUBLISH_WEBFEED)) {
            broadcastService = ServiceRegistry.broadcastService();
        }

        this.userId = user.userId().name();
    }

    @Override
    public BasketService basketService() throws AuthorizationException {
        if (basketService == null)
            throw new AuthorizationException();

        return basketService;
    }

    @Override
    public ReleaseSearchService releaseSearchService() throws AuthorizationException {
        if (releaseSearchService == null)
            throw new AuthorizationException();

        return releaseSearchService;
    }

    @Override
    public SellService sellService() throws AuthorizationException {
        if (sellService == null)
            throw new AuthorizationException();

        return sellService;
    }

    @Override
    public CustomerRepository customerRepository() throws AuthorizationException {
        if (customerRepository == null)
            throw new AuthorizationException();

        return customerRepository;
    }

    @Override
    public BroadcastService broadcastService() throws AuthorizationException {
        if(broadcastService == null)
            throw new AuthorizationException();

        return broadcastService;
    }

    @Override
    public String getUserId() {
        return this.userId;
    }
}
