package at.fhv.ae.backend.domain.repository;

import at.fhv.ae.backend.domain.model.release.Release;
import at.fhv.ae.backend.domain.model.release.ReleaseId;

public interface ReleaseRepository {

    Release findById(ReleaseId id);
}
