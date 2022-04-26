package at.fhv.ae.backend.middleware.rmi.services;

import at.fhv.ae.shared.dto.basket.CustomerSearchResponseDTO;
import at.fhv.ae.shared.repository.CustomerRepository;
import at.fhv.ae.shared.rmi.RemoteCustomerSearchService;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

@Stateful
public class RemoteCustomerSearchServiceImpl implements RemoteCustomerSearchService {

    private CustomerRepository customerRepository;

    public RemoteCustomerSearchServiceImpl(CustomerRepository customerRepository) {
        super();
        this.customerRepository = customerRepository;
    }

    public RemoteCustomerSearchServiceImpl() {

    }

    @Override
    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerSearchResponseDTO> findCustomerByName(String firstName, String lastName) {
        try {
            return customerRepository.findByName(firstName, lastName);
        } catch(RemoteException e) {
            throw new IllegalStateException("Couldn't connect to customer DB");
        }

    }
}
