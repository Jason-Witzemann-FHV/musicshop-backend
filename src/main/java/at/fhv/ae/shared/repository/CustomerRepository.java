package at.fhv.ae.shared.repository;

import at.fhv.ae.shared.dto.customer.Customer;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CustomerRepository extends Remote {

    Customer find(String id) throws RemoteException;
}
