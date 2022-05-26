package at.fhv.ae.playlist.health.readiness;

import at.fhv.ae.playlist.auth.AuthenticatedUserProducer;
import at.fhv.ae.playlist.presentation.PlaylistRestController;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import javax.inject.Inject;

@Readiness
public class AddToPlaylist implements HealthCheck {

    @ConfigProperty(name = "default.userName")
    String userName;

    @ConfigProperty(name = "default.release")
    String release;

    @Inject
    PlaylistRestController playlistRestController;

    @Inject
    AuthenticatedUserProducer userProducer;

    @Override
    public HealthCheckResponse call() {
        try {
            //userProducer.handleAuthenticationEvent(userName);
            playlistRestController.addRelease(userName, release);

            return HealthCheckResponse.named("Add to Playlist Readiness").up().build();
        } catch (Exception e) {
            return HealthCheckResponse.named("Add to Playlist Readiness").down().build();
        }

    }
}


