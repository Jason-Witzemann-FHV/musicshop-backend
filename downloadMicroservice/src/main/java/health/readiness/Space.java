package health.readiness;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import javax.enterprise.context.ApplicationScoped;
import java.io.File;

@Readiness
@ApplicationScoped
public class Space implements HealthCheck {
    public HealthCheckResponse call() {

        File file = new File("/");

        long totalSpace = file.getTotalSpace() / 1024 / 1024 / 1024;
        long freeSpace = file.getFreeSpace() / 1024 / 1024 / 1024;
        long usableSpace = file.getUsableSpace() / 1024 / 1024 / 1024;

        return HealthCheckResponse
                .named("2 - " + Space.class.getSimpleName() + " - Readiness")
                .withData("totalSpace in GB", totalSpace)
                .withData("remainingSpace in GB", freeSpace)
                .withData("usableSpace in GB", usableSpace)
                .status(freeSpace > 10)
                .build();
    }
}
