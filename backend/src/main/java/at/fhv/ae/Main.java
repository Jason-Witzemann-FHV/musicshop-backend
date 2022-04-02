package at.fhv.ae;

import at.fhv.ae.backend.ServiceRegistry;

import at.fhv.ae.backend.infrastructure.LdapCredentialsService;
import at.fhv.ae.backend.middleware.common.impl.SessionFactoryImpl;
import at.fhv.ae.backend.middleware.rmi.facade.RemoteSessionFactoryImpl;
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
import java.util.Set;
import java.util.function.Function;


public class Main {
    public static void main(String[] args) {

        final String ldap = "ldap://10.0.40.161:389";
        final Function<String, String> usernameToDistinguishedName = username ->
                "cn=" + username + ",ou=employees,dc=ad,dc=teama,dc=net";

        try {
            LocateRegistry.createRegistry(Registry.REGISTRY_PORT);

            Naming.rebind("rmi://localhost/music-shop", new RemoteSessionFactoryImpl(
                    new SessionFactoryImpl(
                            new LdapCredentialsService(ldap, usernameToDistinguishedName),
                            ServiceRegistry.userRepository()
                    )
            ));

        } catch (RemoteException | MalformedURLException e) {
            e.printStackTrace();
        }

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
