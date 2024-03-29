package at.fhv.ae.backend.infrastructure;

import at.fhv.ae.backend.ServiceRegistry;
import at.fhv.ae.backend.domain.model.release.ReleaseId;
import at.fhv.ae.backend.domain.model.sale.*;
import at.fhv.ae.backend.domain.model.user.UserId;
import at.fhv.ae.backend.domain.repository.SaleRepository;
import at.fhv.ae.backend.infrastructure.hibernate.HibernateSaleRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class HibernateSaleRepositoryTests {

    private final EntityManager em = ServiceRegistry.entityManager();
    private final SaleRepository saleRepository = new HibernateSaleRepository(em);

    @Test
    void given_sale_when_added_then_find_by_id_returns_sale() {

        var saleItems = List.of(
                new Item(new ReleaseId(UUID.randomUUID()), 2, 9.99),
                new Item(new ReleaseId(UUID.randomUUID()), 3, 19.99),
                new Item(new ReleaseId(UUID.randomUUID()), 1, 29.99)
        );

        var sale = Sale.create(
                new SaleId(1),
                new UserId("nsu3146"),
                ObjectId.get(),
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

    // no sales
    @Test
    void given_no_sale_when_get_sales_of_employee_return_empty_list() {

        var transaction = em.getTransaction();
        transaction.begin();
        var actual = saleRepository.allSales();
        transaction.rollback();

        assertTrue(actual.isEmpty());

    }

    // all sales
    @Test
    void given_sales_when_get_sales_for_employee_then_return_sales() {
        var saleItems = List.of(
                new Item(new ReleaseId(UUID.randomUUID()), 2, 9.99),
                new Item(new ReleaseId(UUID.randomUUID()), 3, 19.99),
                new Item(new ReleaseId(UUID.randomUUID()), 1, 29.99)
        );

        var sale = Sale.create(
                new SaleId(1),
                new UserId("nsu3146"),
                ObjectId.get(),
                PaymentType.CASH, // First sprint only supports cash sale
                SaleType.IN_PERSON,
                saleItems
        );

        var transaction = em.getTransaction();
        transaction.begin();
        saleRepository.addSale(sale);
        em.flush();
        var actual = saleRepository.allSales();
        transaction.rollback();

        assertEquals(1, actual.size());
    }

    @Test
    void given_no_sales_when_getting_next_number_return_zero() {
        // no sales yet, start with 1
        assertEquals(1, saleRepository.next_sequenceNumber());
    }

    @Test
    void given_one_sales_when_getting_next_number_return_right_one() {
        var saleItems = List.of(
                new Item(new ReleaseId(UUID.randomUUID()), 2, 9.99),
                new Item(new ReleaseId(UUID.randomUUID()), 3, 19.99),
                new Item(new ReleaseId(UUID.randomUUID()), 1, 29.99)
        );

        var sale = Sale.create(
                new SaleId(3),
                new UserId("nsu3146"),
                ObjectId.get(),
                PaymentType.CASH, // First sprint only supports cash sale
                SaleType.IN_PERSON,
                saleItems
        );

        var transaction = em.getTransaction();
        transaction.begin();
        saleRepository.addSale(sale);
        em.flush();
        var next_sequence = saleRepository.next_sequenceNumber();
        transaction.rollback();

        assertEquals(4, next_sequence);
    }
}
