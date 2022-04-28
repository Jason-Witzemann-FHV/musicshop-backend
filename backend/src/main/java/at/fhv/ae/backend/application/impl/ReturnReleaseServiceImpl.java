package at.fhv.ae.backend.application.impl;

import at.fhv.ae.backend.application.ReturnReleaseService;
import at.fhv.ae.backend.domain.model.release.ReleaseId;
import at.fhv.ae.backend.domain.model.sale.Item;
import at.fhv.ae.backend.domain.model.sale.SaleId;
import at.fhv.ae.backend.domain.repository.ReleaseRepository;
import at.fhv.ae.backend.domain.repository.SaleRepository;

import javax.ejb.EJB;
import java.util.UUID;

public class ReturnReleaseServiceImpl implements ReturnReleaseService {

    @EJB
    ReleaseRepository releaseRepository;

    @EJB
    SaleRepository saleRepository;


    @Override
    public void returnRelease(UUID saleNumb, UUID releaseId, int amount) {

        var release = releaseRepository.findById(new ReleaseId(releaseId)).orElseThrow();
        var sale = saleRepository.findById(new SaleId(saleNumb)).orElseThrow();

        sale.returnItems(release.releaseId(), amount);

    }
}
