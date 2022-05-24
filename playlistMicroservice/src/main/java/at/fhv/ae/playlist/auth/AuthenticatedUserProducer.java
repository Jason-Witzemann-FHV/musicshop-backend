package at.fhv.ae.playlist.auth;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;


@RequestScoped
public class AuthenticatedUserProducer {

    @Produces
    @RequestScoped
    @AuthenticatedUser
    User authenticatedUser;

    public void handleAuthenticationEvent(@Observes @AuthenticatedUser String username) {
        this.authenticatedUser = new User(username);
    }
}