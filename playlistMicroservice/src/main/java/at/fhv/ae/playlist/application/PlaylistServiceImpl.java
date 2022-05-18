package at.fhv.ae.playlist.application;

import at.fhv.ae.playlist.domain.PlaylistRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class PlaylistServiceImpl implements PlaylistService{

    @Inject
    PlaylistRepository playlistRepository;

    @Override
    public List<PlaylistReleaseDTO> playlist() {

        return playlistRepository.playlist();
    }
}
