package at.fhv.ae.shared.dto.sale;

import lombok.Value;

import java.io.Serializable;
import java.util.List;

@Value
public class SaleItemsRemoteDTO implements Serializable {
    int saleNumber;
    String dateOfSale;
    String customerId;
    double totalPrice;
    List<ItemRemoteDTO> items;
}
