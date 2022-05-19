package at.fhv.ae.playlist.application;


import at.fhv.ae.playlist.domain.ReleaseId;

import java.util.List;

public interface PlaylistService {

    void addToPlaylist(String userId, ReleaseId releaseId);

    List<PlaylistReleaseDTO> playlist(String userId);
}
