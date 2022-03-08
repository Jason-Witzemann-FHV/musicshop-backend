package at.fhv.ae;

import at.fhv.ae.domain.repositories.CustomerRepository;
import at.fhv.ae.infrastructure.RemoteCustomerRepository;

import java.rmi.RemoteException;


public class Main {
    public static void main(String[] args) throws RemoteException {

        CustomerRepository rjcr = new RemoteCustomerRepository();
        var result = rjcr.find("6221173ce0db2b163e992b7f");
    }

}
