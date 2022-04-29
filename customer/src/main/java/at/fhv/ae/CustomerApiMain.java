package at.fhv.ae;


import at.fhv.ae.customer.infrastructure.RemoteCustomerRepositoryImpl;
import at.fhv.ae.shared.repository.CustomerRepository;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class CustomerApiMain {
    public static void main(String[] args) {
        try {
            CustomerRepository customerRepo = new RemoteCustomerRepositoryImpl();
            LocateRegistry.createRegistry(10990);
            Naming.rebind("rmi://10.0.40.161:10990/customer-repository", customerRepo);

            System.out.println("customer repo bound");
        } catch (RemoteException | MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
