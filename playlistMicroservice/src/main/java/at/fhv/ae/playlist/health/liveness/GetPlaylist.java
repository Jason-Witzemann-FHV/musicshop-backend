package at.fhv.ae.playlist.health.liveness;
import at.fhv.ae.playlist.auth.AuthenticatedUserProducer;
import at.fhv.ae.playlist.presentation.PlaylistRestController;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Liveness
@ApplicationScoped
public class GetPlaylist implements HealthCheck {

    @ConfigProperty(name = "default.userName")
    String userName;

    @Inject
    PlaylistRestController playlistRestController;

    @Inject
    AuthenticatedUserProducer userProducer;

    @Override
    public HealthCheckResponse call() {

        try {
            userProducer.handleAuthenticationEvent(userName);
            playlistRestController.getPlaylist();
            return HealthCheckResponse.named("2 - GET Playlist").up().build();
        } catch(Exception e){
            return HealthCheckResponse.named("2 - GET Playlist").down().build();
        }
    }
}
