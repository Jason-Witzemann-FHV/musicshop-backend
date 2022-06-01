package at.fhv.ae.playlist.health.readiness;
import io.smallrye.health.checks.InetAddressHealthCheck;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.Readiness;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class URLCheck {
    @Readiness
    HealthCheck checkURL() {

        return new InetAddressHealthCheck("localhost")
                .name("5 - " + URLCheck.class.getSimpleName() + " - Readiness");
    }

}

