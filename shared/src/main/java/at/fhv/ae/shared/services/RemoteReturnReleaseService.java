package at.fhv.ae.shared.services;

import javax.ejb.Remote;
import java.util.UUID;

@Remote
public interface RemoteReturnReleaseService {

    void returnRelease(int saleNumber, UUID releaseId, int amount);
}
