package at.fhv.ae.backend.application.dto;

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
