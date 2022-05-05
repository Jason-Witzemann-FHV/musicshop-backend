package at.fhv.ae.backend.application;

import javax.ejb.Local;
import java.util.UUID;

@Local
public interface ReturnReleaseService {

    void returnRelease(int saleNumb, UUID releaseId, int amount);
}
