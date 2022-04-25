package at.fhv.ae.shared.rmi;

import at.fhv.ae.shared.dto.basket.CustomerSearchResponseDTO;

import javax.ejb.Local;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RemoteCustomerSearchService extends Remote {

    List<CustomerSearchResponseDTO> findCustomerByName(String firstName, String lastName) throws RemoteException;

}
