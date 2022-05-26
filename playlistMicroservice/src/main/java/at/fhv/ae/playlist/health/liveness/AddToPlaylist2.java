package at.fhv.ae.playlist.health.liveness;

import at.fhv.ae.playlist.auth.AuthenticatedUser;
import at.fhv.ae.playlist.auth.AuthenticatedUserProducer;
import at.fhv.ae.playlist.auth.Secured;
import at.fhv.ae.playlist.auth.User;
import at.fhv.ae.playlist.presentation.PlaylistRestController;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;


@Liveness
@ApplicationScoped
public class AddToPlaylist2 implements HealthCheck {

    @ConfigProperty(name = "default.release")
    String release;

    @Inject
    PlaylistRestController playlistRestController;

    @Inject
    @AuthenticatedUser
    User user;

    @Inject
    AuthenticatedUserProducer userProducer;


    @Override
    @Secured
    public HealthCheckResponse call() {

        try {
            playlistRestController.addRelease(user.getUserId(), release);

            return HealthCheckResponse.named("Add to Playlist").up().build();
        } catch(Exception e){
            return HealthCheckResponse.named("Add to Playlist").down().build();
        }

    }
}
