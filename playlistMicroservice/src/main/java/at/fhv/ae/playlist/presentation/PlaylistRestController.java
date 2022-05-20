package at.fhv.ae.playlist.presentation;

import at.fhv.ae.playlist.domain.Playlist;
import at.fhv.ae.playlist.domain.Release;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/playlist")
public class PlaylistRestController {

    @PUT
    @Path("/add/{userId}/{releaseId}")
    @Transactional
    @Produces(MediaType.TEXT_PLAIN)
    public Response addRelease(@PathParam("userId") String userId, @PathParam("releaseId") String releaseId) {

        try {
            Release release = Release.findById(releaseId);
            Playlist playlist = Playlist.findById(userId);

            playlist.addRelease(release);
            return Response.ok().build();
        } catch(Exception e){
            return Response.notModified().build();
        }

    }

    @GET
    @Path("/{userId}")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlaylist(@PathParam("userId") String userId) {
        Playlist playlist = Playlist.findById(userId);

        //serialisierung funktioniert nicht?
        var results = playlist.allReleases();

        if(!results.isEmpty()) {
            return Response.ok(results).build();
        } else {
            return Response.noContent().build();
        }
    }
}
