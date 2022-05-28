package at.fhv.ae.playlist.health.readiness;
import io.smallrye.health.checks.InetAddressHealthCheck;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import io.smallrye.health.checks.UrlHealthCheck;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.HttpMethod;

@ApplicationScoped
public class URLCHECK {
    @Readiness
    HealthCheck checkURL() {
        return new InetAddressHealthCheck("localhost").name("5 - localhost available");
    }
}

