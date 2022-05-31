package health.readiness;

import io.smallrye.health.checks.SystemLoadHealthCheck;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.Readiness;

import javax.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class SystemLoad {

    @Readiness
    public HealthCheck checkSystemLoad() {
        return new SystemLoadHealthCheck();
    }
}