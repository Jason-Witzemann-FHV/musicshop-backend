package at.fhv.ae;

import at.fhv.ae.backend.ServiceRegistry;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.function.Function;


public class Main {
    public static void main(String[] args) {

       // ServiceRegistry.newsPublisherService(); // skip lazy loading for debugging

        try {
            LocateRegistry.createRegistry(Registry.REGISTRY_PORT);


        } catch (RemoteException  e) {
            e.printStackTrace();
        }

    }

}
