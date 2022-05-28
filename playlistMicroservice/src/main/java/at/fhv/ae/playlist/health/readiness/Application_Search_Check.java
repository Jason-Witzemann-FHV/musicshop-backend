package at.fhv.ae.playlist.health.readiness;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import io.smallrye.health.checks.UrlHealthCheck;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.HttpMethod;

@ApplicationScoped
public class Application_Search_Check {
    @ConfigProperty(name = "default.search")
    String searchURL;

    @Readiness
    HealthCheck detailedInformation() {
        return new UrlHealthCheck(searchURL + "/id/b1704978-3db5-4242-9ef8-19a042d65b04")
                .name("3 - DetailedInformation from a Release")
                .requestMethod(HttpMethod.GET)
                .statusCode(200);

    }

    @Readiness
    HealthCheck searchReleases() {
        return new UrlHealthCheck(searchURL + "/query?title=Jason+20Witzemann's+20Remixes")
                .name("4 - Search releases")
                .requestMethod(HttpMethod.GET)
                .statusCode(200);

    }
}