package at.fhv.ae.playlist.health.liveness;

import at.fhv.ae.playlist.presentation.PlaylistRestController;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;


@Liveness
@ApplicationScoped
public class AddToPlaylistCheck implements HealthCheck {

    @ConfigProperty(name = "default.userName")
    String userName;

    @ConfigProperty(name = "default.release")
    String release;

    @Inject
    PlaylistRestController playlistRestController;

    @Override
    public HealthCheckResponse call() {

        try {
            playlistRestController.addRelease(userName, release);

            return HealthCheckResponse.named("1 - Microservice REST: " + AddToPlaylistCheck.class.getSimpleName() + " - Liveness").up().build();
        } catch(Exception e){
            return HealthCheckResponse.named("1 - Microservice REST: " + AddToPlaylistCheck.class.getSimpleName() + " - Liveness").down().build();
        }

    }
}
