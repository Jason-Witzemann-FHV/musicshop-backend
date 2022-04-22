package at.fhv.ae.shared.rmi;

import at.fhv.ae.shared.dto.sale.SaleItemsRemoteDTO;
import org.bson.types.ObjectId;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RemoteSellService extends Remote {

    /**
     * @param customerId can be null if sale is anonymous
     * @return true on sell success; otherwise false
     */
    boolean sellItemsInBasket(ObjectId customerId) throws RemoteException;

    List<SaleItemsRemoteDTO> salesOfUser() throws RemoteException;

}
