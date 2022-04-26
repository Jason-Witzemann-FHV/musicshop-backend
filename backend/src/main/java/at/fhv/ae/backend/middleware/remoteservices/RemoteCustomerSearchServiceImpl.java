package at.fhv.ae.backend.middleware.remoteservices;

import at.fhv.ae.backend.ServiceRegistry;
import at.fhv.ae.shared.dto.basket.CustomerSearchResponseDTO;
import at.fhv.ae.shared.repository.CustomerRepository;
import at.fhv.ae.shared.services.RemoteCustomerSearchService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.ejb.Stateful;
import java.rmi.RemoteException;
import java.util.List;

@Stateful
@NoArgsConstructor
@AllArgsConstructor
public class RemoteCustomerSearchServiceImpl implements RemoteCustomerSearchService {

    private CustomerRepository customerRepository = ServiceRegistry.customerRepository(); // no bean, this is the RMI connection to Customer Application

    @Override
    public List<CustomerSearchResponseDTO> findCustomerByName(String firstName, String lastName) {
        try {
            return customerRepository.findByName(firstName, lastName);
        } catch(RemoteException e) {
            throw new IllegalStateException("Couldn't connect to customer DB");
        }

    }
}
