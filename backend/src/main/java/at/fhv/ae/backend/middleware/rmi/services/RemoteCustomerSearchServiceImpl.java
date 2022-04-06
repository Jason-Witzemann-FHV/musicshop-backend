package at.fhv.ae.backend.middleware.rmi.services;

import at.fhv.ae.shared.dto.basket.CustomerSearchResponseDTO;
import at.fhv.ae.shared.repository.CustomerRepository;
import at.fhv.ae.shared.rmi.RemoteCustomerSearchService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class RemoteCustomerSearchServiceImpl extends UnicastRemoteObject implements RemoteCustomerSearchService {

    private CustomerRepository customerRepository;

    public RemoteCustomerSearchServiceImpl(CustomerRepository customerRepository) throws RemoteException {
        super();
        this.customerRepository = customerRepository;
    }


    @Override
    public List<CustomerSearchResponseDTO> findCustomerByName(String firstName, String lastName) throws RemoteException {
        return customerRepository.findByName(firstName, lastName);
    }
}
