package at.fhv.ae.backend.domain.repository;

import at.fhv.ae.backend.domain.model.sale.Sale;
import at.fhv.ae.backend.domain.model.sale.SaleId;

import java.util.Optional;

public interface SaleRepository {

    Optional<Sale> findById(SaleId id);

    void addSale(Sale sale);
}
