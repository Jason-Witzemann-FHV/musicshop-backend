package at.fhv.ae.shared.rmi;

import at.fhv.ae.shared.dto.basket.CustomerSearchResponseDTO;
import at.fhv.ae.shared.repository.CustomerRepository;

import javax.ejb.Local;
import javax.ejb.Remote;
import java.rmi.RemoteException;
import java.util.List;

@Remote
public interface RemoteCustomerSearchService {

    void setCustomerRepository(CustomerRepository customerRepository);

    List<CustomerSearchResponseDTO> findCustomerByName(String firstName, String lastName);

}
