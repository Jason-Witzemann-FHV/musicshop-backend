package at.fhv.ae.playlist.infrastructure;

import at.fhv.ae.playlist.application.PlaylistReleaseDTO;
import at.fhv.ae.playlist.domain.PlaylistRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class DummyPlaylistRepository implements PlaylistRepository {

    List<PlaylistReleaseDTO> dummyData = List.of(
            new PlaylistReleaseDTO("Roar", "Katy Perry", 300),
            new PlaylistReleaseDTO("Believer", "Imagine Dragons", 430),
            new PlaylistReleaseDTO("Never gonna give you up", "Rick Astley", 420)
    );

    @Override
    public List<PlaylistReleaseDTO> playlist() {
        return dummyData;
    }
}
