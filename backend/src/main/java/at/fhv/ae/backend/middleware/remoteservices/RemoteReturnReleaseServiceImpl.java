package at.fhv.ae.backend.middleware.remoteservices;

import at.fhv.ae.backend.application.ReturnReleaseService;
import at.fhv.ae.shared.services.RemoteReturnReleaseService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.UUID;

@Stateless
@NoArgsConstructor
@AllArgsConstructor
public class RemoteReturnReleaseServiceImpl implements RemoteReturnReleaseService {

    @EJB
    private ReturnReleaseService returnReleaseService;

    @Override
    public void returnRelease(UUID saleNumber, UUID releaseId, int amount) {
        returnReleaseService.returnRelease(saleNumber, releaseId, amount);
    }

}
