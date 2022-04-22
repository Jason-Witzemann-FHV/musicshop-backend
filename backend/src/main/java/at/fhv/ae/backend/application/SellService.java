package at.fhv.ae.backend.application;

import at.fhv.ae.backend.application.dto.SaleItemsDTO;
import at.fhv.ae.backend.application.exceptions.OutOfStockException;
import at.fhv.ae.backend.domain.model.sale.Sale;
import at.fhv.ae.backend.domain.model.user.UserId;
import at.fhv.ae.shared.dto.sale.SaleItemsRemoteDTO;
import org.bson.types.ObjectId;

import java.util.List;

public interface SellService {

    /**
     * @param customerId can be null if sale is anonymous
     */
    void sellItemsInBasket(String userId, ObjectId customerId) throws OutOfStockException;

    List<SaleItemsDTO> salesOfUser(String userId);

}
