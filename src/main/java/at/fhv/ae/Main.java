package at.fhv.ae;

import at.fhv.ae.backend.domain.model.sale.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Test");
        EntityManager em = emf.createEntityManager();

        List<Item> list = List.of(
                new Item(null, "4", 1, 6.99 ),
                new Item(null, "5", 1, 7.99 )
        );

        em.getTransaction().begin();
        Sale sale = new Sale(null, new SaleId(UUID.randomUUID()), "1", "1", PaymentType.Cash, SaleType.InPerson, list);
        em.persist(sale);
        em.getTransaction().commit();
    }
}
