package at.fhv.ae.playlist.application;

import at.fhv.ae.playlist.domain.Playlist;
import at.fhv.ae.playlist.domain.PlaylistRepository;
import at.fhv.ae.playlist.domain.Release;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PlaylistServiceImpl implements PlaylistService{

    @Inject
    PlaylistRepository playlistRepository;


    @Override
    public void addToPlaylist(String playlistId, ReleaseId releaseId) {
        Release release = playlistRepository.findByReleaseId(releaseId);
        Playlist playlist = playlistRepository.findByPlaylistId(playlistId);

        playlist.addRelease(release);

        //fraglich ob das überhaupt notwendig ist
        playlistRepository.addToPlaylist(playlist, release);
    }


    @Override
    public List<PlaylistReleaseDTO> playlist(String playlistId) {
        Playlist playlist = playlistRepository.findByPlaylistId(playlistId);

        return playlistRepository.playlist(playlist).stream()
                .map(release -> new PlaylistReleaseDTO(
                        release.title(),
                        release.artist(),
                        release.duration()
                )).collect(Collectors.toList());
    }
}
