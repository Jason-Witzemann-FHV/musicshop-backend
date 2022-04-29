package at.fhv.ae.shared.services;

import at.fhv.ae.shared.dto.basket.CustomerSearchResponseDTO;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface RemoteCustomerSearchService {

    List<CustomerSearchResponseDTO> findCustomerByName(String firstName, String lastName);

}
