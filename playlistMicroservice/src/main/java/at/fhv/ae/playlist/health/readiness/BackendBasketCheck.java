package at.fhv.ae.playlist.health.readiness;
import at.fhv.ae.playlist.auth.AuthenticatedUserProducer;
import io.smallrye.health.checks.UrlHealthCheck;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.Readiness;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.HttpMethod;

@ApplicationScoped
public class BackendBasketCheck {
    @ConfigProperty(name = "default.basket")
    String basketUrl;

    @ConfigProperty(name = "default.userName")
    String userName;

    @Inject
    AuthenticatedUserProducer userProducer;

    @Readiness
    HealthCheck addToBasket() {
        return new UrlHealthCheck(basketUrl)
                .name("3 - Backend REST: " + BackendBasketCheck.class.getSimpleName() + " - Add to basket Readiness")
                .requestMethod(HttpMethod.PUT)
                .statusCode(401);
    }
}
