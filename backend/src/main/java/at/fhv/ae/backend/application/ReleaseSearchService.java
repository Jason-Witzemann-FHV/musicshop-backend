package at.fhv.ae.backend.application;

import at.fhv.ae.backend.application.dto.DetailedReleaseDTO;
import at.fhv.ae.backend.application.dto.ReleaseDTO;
import at.fhv.ae.backend.domain.model.work.Genre;

import javax.ejb.Local;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Local
public interface ReleaseSearchService {

    List<ReleaseDTO> query(String title, String artist,String genre);

    Optional<DetailedReleaseDTO> detailedInformation(UUID releaseId);

    List<Genre> knownGenres();
}
