package at.fhv.ae.backend.application;

import java.util.UUID;

public interface ReturnReleaseService {

    void returnRelease(UUID saleNumb, UUID releaseId, int amount);
}
