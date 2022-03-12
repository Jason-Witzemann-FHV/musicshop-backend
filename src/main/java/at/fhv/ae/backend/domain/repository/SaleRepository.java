package at.fhv.ae.backend.domain.repository;

import at.fhv.ae.backend.domain.model.sale.Sale;
import at.fhv.ae.backend.domain.model.sale.SaleId;

public interface SaleRepository {

    Sale find(SaleId id);



}
