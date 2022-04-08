package at.fhv.ae.backend.domain.repository;

import at.fhv.ae.backend.domain.model.release.Release;
import at.fhv.ae.backend.domain.model.release.ReleaseId;
import at.fhv.ae.backend.domain.model.work.Genre;

import java.util.List;
import java.util.Optional;

public interface ReleaseRepository {

    Optional<Release> findById(ReleaseId id);

    List<Release> query(String title, String artist, Genre genre);

    Optional<Integer> currentStock(ReleaseId releaseId);

    void decreaseStock(ReleaseId releaseId, int amount);
}
