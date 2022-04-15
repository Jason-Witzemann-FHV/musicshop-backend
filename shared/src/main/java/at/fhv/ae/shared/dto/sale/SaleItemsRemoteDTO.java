package at.fhv.ae.shared.dto.sale;

import lombok.Value;

import java.io.Serializable;
import java.util.ArrayList;

@Value
public class SaleItemsRemoteDTO implements Serializable {
    String saleNumber;
    String dateOfSale;
    String customerId;
    double totalPrice;
    ArrayList<ItemRemoteDTO> items;
}
