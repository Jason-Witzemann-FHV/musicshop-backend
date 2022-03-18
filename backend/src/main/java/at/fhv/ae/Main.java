package at.fhv.ae;

import at.fhv.ae.backend.application.ReleaseQueryService;
import at.fhv.ae.backend.application.dto.ReleaseDTO;
import at.fhv.ae.backend.application.impl.ReleaseQueryServiceImpl;
import at.fhv.ae.backend.domain.model.release.*;
import at.fhv.ae.backend.domain.model.sale.*;
import at.fhv.ae.backend.domain.model.work.*;
import at.fhv.ae.backend.infrastructure.HibernateReleaseRepository;
import at.fhv.ae.backend.middleware.ReleaseSearchServiceImpl;
import at.fhv.ae.shared.dto.customer.Customer;
import at.fhv.ae.shared.repository.CustomerRepository;
import at.fhv.ae.shared.rmi.ReleaseSearchService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.UUID;


public class Main {
    public static void main(String[] args) {


        try {
            LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            Naming.rebind("rmi://localhost/release-search-service", new ReleaseSearchServiceImpl());

            //ReleaseSearchService rss = (ReleaseSearchService)Naming.lookup("rmi://localhost/release-search-service");
            //rss.query("Best Song Ever", "astley", "pop").forEach(System.out::println);

        } catch (RemoteException | MalformedURLException e) {
            e.printStackTrace();
        }

        //EntityManagerFactory emf = Persistence.createEntityManagerFactory("Test");
        //EntityManager em = emf.createEntityManager();

       // insertDemoRelease(em);

        //retrieveDemoRelease(em).forEach(System.out::println);

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

}
