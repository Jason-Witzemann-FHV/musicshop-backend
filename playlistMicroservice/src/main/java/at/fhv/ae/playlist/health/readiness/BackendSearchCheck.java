package at.fhv.ae.playlist.health.readiness;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import io.smallrye.health.checks.UrlHealthCheck;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.HttpMethod;

@ApplicationScoped
public class BackendSearchCheck {

    @ConfigProperty(name = "default.userName")
    String userName;

    @ConfigProperty(name = "default.search")
    String searchURL;

    @Readiness
    HealthCheck searchReleaseById() {
        return new UrlHealthCheck(searchURL + "/id/ec8c15b9-18e2-47d0-ad7a-0c45438c7302")
                .name("3 - Backend REST: " + BackendSearchCheck.class.getSimpleName() + " - Query by   Readiness")
                .requestMethod(HttpMethod.GET)
                .statusCode(200);

    }

    @Readiness
    HealthCheck searchReleasesByTitle() {
        return new UrlHealthCheck(searchURL + "/query?title=Jason+20Witzemann's+20Remixes")
                .name("4 - Backend REST: " + BackendSearchCheck.class.getSimpleName() + " - Query by Title Readiness")
                .requestMethod(HttpMethod.GET)
                .statusCode(200);
    }
}