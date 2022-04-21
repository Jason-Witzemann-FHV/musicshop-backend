package at.fhv.ae;

import at.fhv.ae.backend.ServiceRegistry;

import at.fhv.ae.backend.infrastructure.LdapCredentialsService;
import at.fhv.ae.backend.middleware.common.impl.SessionFactoryImpl;
import at.fhv.ae.backend.middleware.rmi.facade.RemoteSessionFactoryImpl;
import at.fhv.ae.shared.rmi.RemoteSessionFactory;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.function.Function;


public class Main {
    public static void main(String[] args) {

        ServiceRegistry.newsPublisherService(); // skip lazy loading for debugging

        final String ldap = "ldap://10.0.40.161:389";
        final Function<String, String> usernameToDistinguishedName = username ->
                "cn=" + username + ",ou=employees,dc=ad,dc=teama,dc=net";

        try {
            LocateRegistry.createRegistry(Registry.REGISTRY_PORT);

            RemoteSessionFactory factory =  new RemoteSessionFactoryImpl(
                    new SessionFactoryImpl(
                            new LdapCredentialsService(ldap, usernameToDistinguishedName),
                            ServiceRegistry.userRepository()
                    )
            );

            Naming.rebind("rmi://localhost/music-shop", factory);


        } catch (RemoteException | MalformedURLException e) {
            e.printStackTrace();
        }

    }

}
