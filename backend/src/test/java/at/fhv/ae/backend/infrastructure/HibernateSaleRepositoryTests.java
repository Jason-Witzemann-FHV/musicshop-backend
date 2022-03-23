package at.fhv.ae.backend.infrastructure;

import at.fhv.ae.backend.ServiceRegistry;
import at.fhv.ae.backend.domain.model.release.ReleaseId;
import at.fhv.ae.backend.domain.model.sale.*;
import at.fhv.ae.backend.domain.repository.SaleRepository;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


class HibernateSaleRepositoryTests {

    private final EntityManager em = ServiceRegistry.entityManager();
    private final SaleRepository saleRepository = ServiceRegistry.saleRepository();

    @Test
    void given_sale_when_added_then_find_by_id_returns_sale() {


        var tx = em.getTransaction();
        tx.begin();
        em.createNativeQuery("DROP TABLE IF EXISTS sale_item");
        em.createNativeQuery("DROP TABLE IF EXISTS sale");
        em.createNativeQuery("DROP TABLE IF EXISTS item");
        tx.commit();

        var saleItems = List.of(
                new Item(new ReleaseId(UUID.randomUUID()), 2, 9.99),
                new Item(new ReleaseId(UUID.randomUUID()), 3, 19.99),
                new Item(new ReleaseId(UUID.randomUUID()), 1, 29.99)
        );

        var sale = Sale.create(new SaleId(UUID.randomUUID()),
                "anonymous Employee",
                "anonymous Customer",
                PaymentType.CASH, // First sprint only supports cash sale
                SaleType.IN_PERSON,
                saleItems
        );

        var transaction = em.getTransaction();
        transaction.begin();
        saleRepository.addSale(sale);
        em.flush();
        var actual = saleRepository.findById(sale.saleId());
        transaction.rollback();

        assertTrue(actual.isPresent());
        assertEquals(sale, actual.get());
    }

}
