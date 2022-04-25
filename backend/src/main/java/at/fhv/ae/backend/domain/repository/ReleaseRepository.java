package at.fhv.ae.backend.domain.repository;

import at.fhv.ae.backend.domain.model.release.Release;
import at.fhv.ae.backend.domain.model.release.ReleaseId;
import at.fhv.ae.backend.domain.model.work.Genre;

import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Local
public interface ReleaseRepository {

    Optional<Release> findById(ReleaseId id);

    List<Release> query(String title, String artist, Genre genre);
}
