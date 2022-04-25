package at.fhv.ae.backend.infrastructure;

import at.fhv.ae.backend.ServiceRegistry;
import at.fhv.ae.backend.domain.model.sale.Sale;
import at.fhv.ae.backend.domain.model.sale.SaleId;
import at.fhv.ae.backend.domain.model.user.UserId;
import at.fhv.ae.backend.domain.repository.SaleRepository;
import lombok.AllArgsConstructor;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Stateless
public class HibernateSaleRepository implements SaleRepository {

    private EntityManager em = ServiceRegistry.entityManager();

    @Override
    public Optional<Sale> findById(SaleId id) {
        return em.createQuery("select s from Sale s where s.saleId = :saleId", Sale.class)
                .setParameter("saleId", id)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public void addSale(Sale sale) {
        em.persist(sale);
    }

    @Override
    public List<Sale> salesOfUser(UserId userId) {
        return em.createQuery("select s from Sale s where s.employeeId = :userId", Sale.class)
                .setParameter("userId", userId)
                .getResultList();
    }


}
