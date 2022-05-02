package at.fhv.ae.backend.application;

import javax.ejb.Local;
import java.util.UUID;

@Local
public interface ReturnReleaseService {

    void returnRelease(UUID saleNumb, UUID releaseId, int amount);
}
