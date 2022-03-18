package at.fhv.ae.backend.domain.repository;

import at.fhv.ae.backend.domain.model.release.Release;
import at.fhv.ae.backend.domain.model.release.ReleaseId;

import java.util.List;

public interface ReleaseRepository {

    Release findById(ReleaseId id);

    List<Release> query(String title, String artist,String genre);
}
