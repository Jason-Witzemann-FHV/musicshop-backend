package at.fhv.ae.playlist.domain;

import at.fhv.ae.playlist.application.PlaylistReleaseDTO;

import java.math.BigInteger;
import java.util.List;

public interface PlaylistRepository {

    List<PlaylistReleaseDTO> playlist();

    Playlist findByPlaylistId(String playListId);
    Release findById(String releaseId);



    void addToPlaylist(Playlist playlist, Release release);
}
