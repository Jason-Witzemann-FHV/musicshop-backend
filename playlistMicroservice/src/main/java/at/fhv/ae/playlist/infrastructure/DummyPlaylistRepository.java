package at.fhv.ae.playlist.infrastructure;

import at.fhv.ae.playlist.application.PlaylistReleaseDTO;
import at.fhv.ae.playlist.domain.Playlist;
import at.fhv.ae.playlist.domain.PlaylistRepository;
import at.fhv.ae.playlist.domain.Release;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class DummyPlaylistRepository implements PanacheRepository<Playlist>, PlaylistRepository {

    List<PlaylistReleaseDTO> dummyData = List.of(
            new PlaylistReleaseDTO("Roar", "Katy Perry", 300),
            new PlaylistReleaseDTO("Believer", "Imagine Dragons", 430),
            new PlaylistReleaseDTO("Never gonna give you up", "Rick Astley", 420)
    );

    @Override
    public Release findById(String releaseId) {
       Release release = Release.findById(releaseId);
       return release;
    }

    @Override
    public Playlist findByPlaylistId(String playListId) {
        return Playlist.findById(playListId);
    }

    @Override
    public void addToPlaylist(Playlist playlist, Release release) {
        //unten 35 anderer Ort oder?
        playlist.addRelease(release);
        release.persist();
    }


    @Override
    public List<PlaylistReleaseDTO> playlist() {
        return dummyData;
    }



}
