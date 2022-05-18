package at.fhv.ae.playlist.application;


import at.fhv.ae.playlist.domain.Playlist;
import at.fhv.ae.playlist.domain.Release;

import java.util.List;

public interface PlaylistService {

    void addToPlaylist(String playlistId, ReleaseId releaseId);

    List<Release> playlist(String playlistId);
}
