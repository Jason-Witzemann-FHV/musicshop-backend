package at.fhv.ae.playlist.application;


import java.util.List;

public interface PlaylistService {

    void addToPlaylist(String playlistId, String releaseId);

    List<PlaylistReleaseDTO> playlist();
}
