package at.fhv.ae;

import at.fhv.ae.backend.ServiceRegistry;

import at.fhv.ae.backend.middleware.rmi.services.RemoteSellServiceImpl;
import at.fhv.ae.backend.middleware.rmi.services.RemoteReleaseSearchServiceImpl;
import at.fhv.ae.backend.middleware.rmi.services.RemoteBasketServiceImpl;
import at.fhv.ae.shared.dto.customer.Customer;
import at.fhv.ae.shared.repository.CustomerRepository;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class Main {
    public static void main(String[] args) {


        try {
            LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            Naming.rebind("rmi://localhost/release-search-service", new RemoteReleaseSearchServiceImpl(ServiceRegistry.releaseService()));
            Naming.rebind("rmi://localhost/sell-service", new RemoteSellServiceImpl(ServiceRegistry.sellService()));
            Naming.rebind("rmi://localhost/basket-service", new RemoteBasketServiceImpl(ServiceRegistry.basketService()));

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
