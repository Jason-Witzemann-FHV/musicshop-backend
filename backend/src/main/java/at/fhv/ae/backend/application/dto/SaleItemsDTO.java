package at.fhv.ae.backend.application.dto;

import at.fhv.ae.shared.dto.sale.ItemRemoteDTO;
import lombok.Value;

import java.util.List;

@Value
public class SaleItemsDTO {
    String saleNumber;
    String dateOfSale;
    String customerId;
    double totalPrice;
    List<ItemDTO> items;
}
