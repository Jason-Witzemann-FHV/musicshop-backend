package at.fhv.ae.backend.application;

import at.fhv.ae.backend.application.dto.SaleItemsDTO;
import at.fhv.ae.backend.application.exceptions.InvalidCreditCardException;
import at.fhv.ae.backend.application.exceptions.OutOfStockException;
import at.fhv.ae.shared.dto.customer.CreditCard;
import org.bson.types.ObjectId;

import javax.ejb.Local;
import java.util.List;
import java.util.Optional;

@Local
public interface SellService {

    /**
     * @param customerId can be null if sale is anonymous
     */
    void sellItemsInBasket(String userId, ObjectId customerId) throws OutOfStockException;

    void selfSale(String userId, CreditCard creditCardInfoToCheck) throws OutOfStockException, InvalidCreditCardException;

    List<SaleItemsDTO> allSales();

    Optional<SaleItemsDTO> searchSale(int saleNum);
}
