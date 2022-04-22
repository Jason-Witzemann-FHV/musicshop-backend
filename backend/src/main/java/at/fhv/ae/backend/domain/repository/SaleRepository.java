package at.fhv.ae.backend.domain.repository;

import at.fhv.ae.backend.domain.model.sale.Sale;
import at.fhv.ae.backend.domain.model.sale.SaleId;
import at.fhv.ae.backend.domain.model.user.UserId;

import java.util.List;
import java.util.Optional;

public interface SaleRepository {

    Optional<Sale> findById(SaleId id);

    void addSale(Sale sale);

    List<Sale> salesOfUser(UserId userId);
}
