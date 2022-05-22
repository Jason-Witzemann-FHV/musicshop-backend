package at.fhv.ae.playlist.presentation;

import at.fhv.ae.playlist.auth.AuthenticatedUser;
import at.fhv.ae.playlist.auth.Secured;
import at.fhv.ae.playlist.auth.User;
import at.fhv.ae.playlist.domain.Playlist;
import at.fhv.ae.playlist.domain.Song;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("/playlist")
public class PlaylistRestController {

    @Inject
    @AuthenticatedUser
    User user;

    @PUT
    @Path("/add/{userId}/{releaseId}")
    @Transactional
    @Produces(MediaType.TEXT_PLAIN)
    public Response addRelease(@PathParam("userId") String userId, @PathParam("releaseId") String songId) {

        Song song = Song.findById(UUID.fromString(songId));
        if (song == null) {
            Response.status(400, "SongId " + songId + " is not known");
        }

        Playlist playlist = Playlist.findById(userId);
        if (playlist == null) {
            playlist = new Playlist(userId);
            playlist.persist();
        }

        playlist.addSong(song);
        return Response.ok().build();
    }

    @GET
    @Transactional
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlaylist() {
        Playlist playlist = Playlist.findById(user.getUserId());
        if (playlist == null) {
            return Response.noContent().build();
        } else if (playlist.allSongs().isEmpty()) {
            return Response.noContent().build();
        } else {
            return Response.ok(playlist.allSongs()).build();
        }
    }
}
