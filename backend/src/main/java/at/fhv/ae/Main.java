package at.fhv.ae;

import at.fhv.ae.backend.application.ReleaseQueryService;
import at.fhv.ae.backend.application.dto.ReleaseDTO;
import at.fhv.ae.backend.application.impl.ReleaseQueryServiceImpl;
import at.fhv.ae.backend.domain.model.release.*;
import at.fhv.ae.backend.domain.model.sale.*;
import at.fhv.ae.backend.domain.model.work.*;
import at.fhv.ae.backend.infrastructure.HibernateReleaseRepository;
import at.fhv.ae.shared.dto.customer.Customer;
import at.fhv.ae.shared.repository.CustomerRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.UUID;


public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Test");
        EntityManager em = emf.createEntityManager();

       // insertDemoRelease(em);

        retrieveDemoRelease(em).forEach(System.out::println);

/*
        try {
            System.out.println(retrieveExampleCustomer(connectToRemoteCustomerRepository()));
        } catch (RemoteException | MalformedURLException | NotBoundException e) {
            e.printStackTrace();
        }
*/

    }

    private static CustomerRepository connectToRemoteCustomerRepository()
            throws MalformedURLException, NotBoundException, RemoteException {

        final String rmiUrl = "rmi://10.0.40.161/customer-repository";
        return (CustomerRepository) Naming.lookup(rmiUrl);
    }

    private static Customer retrieveExampleCustomer(CustomerRepository repo)
            throws RemoteException {

        final String id = "6221173ce0db2b163e992b7f";
        return repo.find(id);
    }

    private static List<ReleaseDTO> retrieveDemoRelease(EntityManager em) {
        ReleaseQueryService rqs = new ReleaseQueryServiceImpl(new HibernateReleaseRepository(em));
        return rqs.query("some", "republic","pop");
    }

    private static void insertDemoRelease(EntityManager em) {
        em.getTransaction().begin();

        List<Item> list = List.of(
                new Item("4", 1, 6.99 ),
                new Item("5", 1, 7.99 )
        );

        Supplier supplier = new Supplier("Gebrüder Weiß", "Musterstraße 1");
        Label label = new Label("Hello", "World");
        Recording recording = getDemoRecording();

        Release release = new Release(new ReleaseId(UUID.randomUUID()), 5, "Some title", Medium.CD, label, List.of(supplier), List.of(recording.recordingId()));
        Sale sale = new Sale(new SaleId(UUID.randomUUID()), "1", "1", PaymentType.CASH, SaleType.IN_PERSON, list);

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
