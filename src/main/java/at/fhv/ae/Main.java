package at.fhv.ae;

import at.fhv.ae.backend.domain.model.release.*;
import at.fhv.ae.backend.domain.model.sale.*;
import at.fhv.ae.backend.domain.model.work.*;

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
                new Item("4", 1, 6.99 ),
                new Item("5", 1, 7.99 )
        );


        em.getTransaction().begin();
        Supplier supplier = new Supplier("Gebrüder Weiß", "Musterstraße 1");
        Label label = new Label("Hello", "World");
        Release release = new Release(new ReleaseId(UUID.randomUUID()), 5, "Some title", Medium.CD, label, List.of(supplier));
        Sale sale = new Sale(new SaleId(UUID.randomUUID()), "1", "1", PaymentType.CASH, SaleType.INPERSON, list);
        Recording recording = getDemoRecording();

        em.persist(sale);
        em.persist(supplier);
        em.persist(label);
        em.persist(release);
        em.persist(recording);
        em.getTransaction().commit();

    }

    private static Recording getDemoRecording() {
        RecordingId recordingId = new RecordingId(UUID.randomUUID());
        Work work = new Work("Counting Stars");
        List<Artist> artists = List.of(new Artist("One Republic"));
        List<Genre> genres = List.of(new Genre("Pop"), new Genre("Electro"));
        return new Recording(
                recordingId,
                "Counting Stars, original",
                241,
                2013,
                work,
                artists,
                genres
        );
    }
}
