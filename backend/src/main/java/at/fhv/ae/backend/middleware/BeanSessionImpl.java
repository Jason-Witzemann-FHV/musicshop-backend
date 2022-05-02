package at.fhv.ae.backend.middleware;

import at.fhv.ae.backend.domain.model.user.Permission;
import at.fhv.ae.backend.domain.model.user.User;
import at.fhv.ae.backend.domain.model.user.UserId;
import at.fhv.ae.backend.domain.repository.UserRepository;
import at.fhv.ae.shared.AuthorizationException;
import at.fhv.ae.shared.services.*;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import java.util.Optional;

@Stateful
public class BeanSessionImpl implements BeanSession {

    @EJB
    private RemoteReleaseSearchService remoteReleaseSearchService;

    @EJB
    private RemoteBasketService remoteBasketService;

    @EJB
    private RemoteSellService remoteSellService;

    @EJB
    private RemoteReturnReleaseService remoteReturnReleaseService;

    @EJB
    private RemoteCustomerSearchService remoteCustomerSearchService;

    @EJB
    private RemoteBroadcastService remoteBroadcastService;

    @EJB
    private RemoteNewsPollingService remoteNewsPublisherService;

    @EJB
    private CredentialsService credentialsService;

    @EJB
    private UserRepository userRepository;

    private User user;

    @Override
    public boolean authenticate(String username, String password) {
        if (!credentialsService.authorize(username, password)) {
            return false;
        }

        userRepository.userById(new UserId(username)).ifPresent( u -> {
            user = u;
            var id = u.userId().toString();
            remoteSellService.init(id);
            remoteBasketService.init(id);
            remoteBroadcastService.init(id);
            remoteNewsPublisherService.init(id);
        });
        return user != null;
    }

    @Override
    public RemoteBasketService remoteBasketService() throws AuthorizationException {
        return Optional.ofNullable(user)
                .stream()
                .filter(u -> u.hasPermission(Permission.SELL_RELEASES))
                .map(u -> remoteBasketService)
                .findFirst()
                .orElseThrow(AuthorizationException::new);
    }

    @Override
    public RemoteSellService remoteSellService() throws AuthorizationException {
        return Optional.ofNullable(user)
                .stream()
                .filter(u -> u.hasPermission(Permission.SELL_RELEASES))
                .map(u -> remoteSellService)
                .findFirst()
                .orElseThrow(AuthorizationException::new);
    }


    @Override
    public RemoteReleaseSearchService remoteReleaseService() throws AuthorizationException {
        return Optional.ofNullable(user)
                .stream()
                .filter(u -> u.hasPermission(Permission.SEARCH_RELEASES))
                .map(u -> remoteReleaseSearchService)
                .findFirst()
                .orElseThrow(AuthorizationException::new);
    }

    @Override
    public RemoteCustomerSearchService remoteCustomerSearchService() throws AuthorizationException {
        return Optional.ofNullable(user)
                .stream()
                .filter(u -> u.hasPermission(Permission.SEARCH_RELEASES))
                .map(u -> remoteCustomerSearchService)
                .findFirst()
                .orElseThrow(AuthorizationException::new);
    }

    @Override
    public RemoteBroadcastService remoteBroadcastService() throws AuthorizationException {
        return Optional.ofNullable(user)
                .stream()
                .filter(u -> u.hasPermission(Permission.PUBLISH_WEBFEED))
                .map(u -> remoteBroadcastService)
                .findFirst()
                .orElseThrow(AuthorizationException::new);
    }

    @Override
    public RemoteNewsPollingService remoteNewsPublisherService() throws AuthorizationException {
        return Optional.ofNullable(user)
                .stream()
                .filter(u -> u.hasPermission(Permission.PUBLISH_WEBFEED))
                .map(u -> remoteNewsPublisherService)
                .findFirst()
                .orElseThrow(AuthorizationException::new);
    }

    @Override
    public RemoteReturnReleaseService remoteReturnReleaseService() throws AuthorizationException {
        return Optional.ofNullable(user)
                .stream()
                .filter(u -> u.hasPermission(Permission.SELL_RELEASES))
                .map(u -> remoteReturnReleaseService)
                .findFirst()
                .orElseThrow(AuthorizationException::new);
    }


}
