package at.fhv.ae.playlist.application;

import at.fhv.ae.playlist.domain.Playlist;
import at.fhv.ae.playlist.domain.PlaylistRepository;
import at.fhv.ae.playlist.domain.Release;
import at.fhv.ae.playlist.domain.ReleaseId;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PlaylistServiceImpl implements PlaylistService{

    @Inject
    PlaylistRepository playlistRepository;


    @Override
    public void addToPlaylist(String userId, ReleaseId releaseId) {
        Release release = playlistRepository.findByReleaseId(releaseId);
        Playlist playlist = playlistRepository.findByUserId(userId);

        playlist.addRelease(release);

        //playlistRepository.addToPlaylist(playlist, release);
    }


    @Override
    public List<PlaylistReleaseDTO> playlist(String userId) {
        Playlist playlist = playlistRepository.findByUserId(userId);

        return playlist.allReleases().stream()
                .map(release -> new PlaylistReleaseDTO(
                        release.title(),
                        release.artist(),
                        release.duration()
                )).collect(Collectors.toList());
    }
}
