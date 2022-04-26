package at.fhv.ae.shared.services;

import at.fhv.ae.shared.dto.sale.SaleItemsRemoteDTO;
import org.bson.types.ObjectId;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface RemoteSellService {


    void init(String userId);

    /**
     * @param customerId can be null if sale is anonymous
     * @return true on sell success; otherwise false
     */
    boolean sellItemsInBasket(ObjectId customerId);

    List<SaleItemsRemoteDTO> salesOfUser();
}
