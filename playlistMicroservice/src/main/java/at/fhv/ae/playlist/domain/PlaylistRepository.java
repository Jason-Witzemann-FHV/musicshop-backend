package at.fhv.ae.playlist.domain;

import at.fhv.ae.playlist.application.PlaylistReleaseDTO;
import at.fhv.ae.playlist.application.ReleaseId;

import java.math.BigInteger;
import java.util.List;

public interface PlaylistRepository {

    Playlist findByPlaylistId(String playListId);

    Release findByReleaseId(ReleaseId releaseId);

    void addToPlaylist(Playlist playlist, Release release);

    List<Release> playlist(Playlist playlist);
}
