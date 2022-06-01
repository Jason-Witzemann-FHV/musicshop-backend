package health.liveness;

import at.fhv.ae.download.DownloadRestController;
import at.fhv.ae.download.auth.AuthenticatedUserProducer;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Liveness
@ApplicationScoped
public class DownloadSongCheck implements HealthCheck {

    @ConfigProperty(name = "default.userName")
    String userName;

    @ConfigProperty(name = "default.release")
    String release;

    @Inject
    DownloadRestController downloadRestController;

    @Inject
    AuthenticatedUserProducer userProducer;

    @Override
    public HealthCheckResponse call() {

        try {
            userProducer.handleAuthenticationEvent(userName);
            downloadRestController.download(release);

            return HealthCheckResponse.named("1 - " + DownloadSongCheck.class.getSimpleName() + " - Liveness") .up().build();
        } catch(Exception e){
            return HealthCheckResponse.named("1 - " + DownloadSongCheck.class.getSimpleName() + " - Liveness").down().build();
        }
    }
}
