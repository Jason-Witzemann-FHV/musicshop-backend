package at.fhv.ae.playlist.domain;

public interface PlaylistRepository {

    Playlist findByUserId(String userId);

    Release findByReleaseId(ReleaseId releaseId);

    void addToPlaylist(Playlist playlist, Release release);

}
