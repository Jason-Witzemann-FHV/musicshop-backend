package at.fhv.ae.backend.application;

import at.fhv.ae.backend.application.dto.DetailedReleaseDTO;
import at.fhv.ae.backend.application.dto.ReleaseDTO;

import java.util.List;
import java.util.UUID;

public interface ReleaseService {

    List<ReleaseDTO> query(String title, String artist,String genre);

    DetailedReleaseDTO detailedInformation(UUID releaseId) throws IllegalArgumentException;
}
