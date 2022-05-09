package at.fhv.ae.backend.application.dto;

import at.fhv.ae.backend.domain.model.sale.Sale;
import lombok.Value;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Value
public class SaleItemsDTO {
    int saleNumber;
    String dateOfSale;
    String customerId;
    double totalPrice;
    List<ItemDTO> items;

    public static SaleItemsDTO fromDomain(Sale sale, List<ItemDTO> items) {
        return new SaleItemsDTO(
                sale.saleId().id(),
                sale.sellTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                sale.customerId() == null ? "Anonymous" : sale.customerId().toString(),
                sale.totalPrice(),
                items
        );
    }
}
