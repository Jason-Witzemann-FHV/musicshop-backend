package at.fhv.ae.shared.repository;

import at.fhv.ae.shared.dto.basket.CustomerSearchResponseDTO;
import at.fhv.ae.shared.dto.customer.Customer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface CustomerRepository extends Remote {

    Customer find(String id) throws RemoteException;

    List<CustomerSearchResponseDTO> findByName(String firstName, String lastName) throws RemoteException;
}
