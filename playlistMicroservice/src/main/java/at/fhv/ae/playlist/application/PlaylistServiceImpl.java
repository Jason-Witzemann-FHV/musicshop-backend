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
    public void addToPlaylist(String playlistId, String releaseId) {
        Release release = playlistRepository.findById(releaseId);
        Playlist playlist = playlistRepository.findByPlaylistId(playlistId);
        playlistRepository.addToPlaylist(playlist, release);
    }

    @Override
    public List<PlaylistReleaseDTO> playlist() {

        return playlistRepository.playlist();
    }
}
