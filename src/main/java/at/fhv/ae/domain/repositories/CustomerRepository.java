package at.fhv.ae.domain.repositories;

import at.fhv.ae.domain.customer.Customer;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CustomerRepository extends Remote {

    Customer find(String id) throws RemoteException;

}
