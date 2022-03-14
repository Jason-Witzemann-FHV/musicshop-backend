package at.fhv.ae;

import at.fhv.ae.shared.repository.CustomerRepository;
import at.fhv.ae.user_backend.infrastructure.RemoteCustomerRepositoryImpl;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CustomerApiMain {
    public static void main(String[] args) {
        try {
            CustomerRepository customerRepo = new RemoteCustomerRepositoryImpl();

            LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            Naming.rebind("rmi://10.0.40.161/customer-repository", customerRepo);

            System.out.println("customer repo bound");
        } catch (RemoteException | MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
