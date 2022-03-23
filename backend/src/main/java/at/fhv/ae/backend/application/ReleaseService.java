package at.fhv.ae.backend.application;

import at.fhv.ae.backend.application.dto.DetailedReleaseDTO;
import at.fhv.ae.backend.application.dto.ReleaseDTO;
import at.fhv.ae.backend.domain.model.release.ReleaseId;

import java.util.List;

public interface ReleaseService {

    List<ReleaseDTO> query(String title, String artist,String genre);

    DetailedReleaseDTO detailedInformation(ReleaseId releaseId) throws IllegalArgumentException;
}
