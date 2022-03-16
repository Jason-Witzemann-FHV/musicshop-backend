package at.fhv.ae.backend.application;

import at.fhv.ae.backend.application.dto.ReleaseDTO;

import java.util.List;

public interface ReleaseQueryService {

    List<ReleaseDTO> query(String title, String artist);
}
