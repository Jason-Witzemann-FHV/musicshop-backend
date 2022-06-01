package at.fhv.ae.playlist.health.readiness;

import javax.enterprise.context.ApplicationScoped;

import io.smallrye.health.checks.SystemLoadHealthCheck;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.Readiness;


@ApplicationScoped
public class SystemLoad {

    @Readiness
    public HealthCheck checkSystemLoad() {
        return new SystemLoadHealthCheck();
    }
}