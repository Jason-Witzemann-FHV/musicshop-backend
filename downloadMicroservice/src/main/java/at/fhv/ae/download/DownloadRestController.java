package at.fhv.ae.download;

import at.fhv.ae.download.auth.AuthenticatedUser;
import at.fhv.ae.download.auth.Secured;
import at.fhv.ae.download.auth.User;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("/download")
public class DownloadRestController {

    @Inject
    @AuthenticatedUser
    User authenticatedUser;

    @GET
    @Path("/{song}")
    @Secured
    @Produces("audio/mpeg")
    public Response download(@PathParam("song") String song) {

        Client client = ClientBuilder.newClient();
        var status = client
                .target("http://localhost:8083/playlist/owns/" + authenticatedUser.getUserId() + "/" + song)
                .request(MediaType.TEXT_PLAIN)
                .get()
                .getStatus();
        client.close();


        if(status != 200) {
            return Response.status(400, "requested download for a song that is not in your playlist.").build();
        }

        return Song.find(UUID.fromString(song))
                .map(s -> Response.ok(s.bytes(), s.contentType()).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + s.downloadName()))
                .orElse(Response.status(404))
                .build();
    }
}