package at.fhv.ae.playlist.application;

import at.fhv.ae.playlist.domain.Playlist;
import at.fhv.ae.playlist.domain.PlaylistRepository;
import at.fhv.ae.playlist.domain.Release;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class PlaylistServiceImpl implements PlaylistService{

    @Inject
    PlaylistRepository playlistRepository;


    @Override
    public void addToPlaylist(String playlistId, ReleaseId releaseId) {
        Release release = playlistRepository.findByReleaseId(releaseId);
        Playlist playlist = playlistRepository.findByPlaylistId(playlistId);
        playlistRepository.addToPlaylist(playlist, release);
    }

    //DTO zurückgeben
    @Override
    public List<Release> playlist(String playlistId) {
        Playlist playlist = playlistRepository.findByPlaylistId(playlistId);
        return playlistRepository.playlist(playlist);
    }
}
