package at.fhv.ae.shared.services;

import javax.ejb.Remote;
import java.util.UUID;

@Remote
public interface RemoteReturnReleaseService {

    void returnRelease(UUID saleNumber, UUID releaseId, int amount);
}
