package at.fhv.ae;

import at.fhv.ae.domain.repositories.CustomerRepository;
import at.fhv.ae.infrastructure.RemoteCustomerRepositoryImpl;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;


public class Main {
    public static void main(String[] args) throws RemoteException {

        try {
            CustomerRepository stub = (CustomerRepository)Naming.lookup("rmi://10.0.40.161/customer-repository");
            var result = stub.find("6221173ce0db2b163e992b7f");
            System.out.println(result);
        } catch (NotBoundException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

}
