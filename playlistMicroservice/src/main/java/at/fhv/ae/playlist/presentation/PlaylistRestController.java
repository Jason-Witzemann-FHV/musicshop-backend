package at.fhv.ae.playlist.presentation;

import at.fhv.ae.playlist.application.PlaylistReleaseDTO;
import at.fhv.ae.playlist.application.PlaylistService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/playlist")
public class PlaylistRestController {

    @Inject
    PlaylistService playlistService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<PlaylistReleaseDTO> playlist() {
        return playlistService.playlist();
    }

    @PUT
    @Path("/add/{playlistId}/{releaseId}")
    @Transactional
    @Produces(MediaType.TEXT_PLAIN)
    public Response update(@PathParam("playlistId") String playlistId, @PathParam("releaseId") String releaseId) {
        playlistService.addToPlaylist(playlistId, releaseId);
        return Response.ok("Release " + releaseId + " added to "+ playlistId).build();

        // fehlercode einfügen ??
    }

}
