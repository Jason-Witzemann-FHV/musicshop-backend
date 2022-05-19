package at.fhv.ae.playlist.application;


import java.util.List;

public interface PlaylistService {

    void addToPlaylist(String playlistId, ReleaseId releaseId);

    List<PlaylistReleaseDTO> playlist(String playlistId);
}
