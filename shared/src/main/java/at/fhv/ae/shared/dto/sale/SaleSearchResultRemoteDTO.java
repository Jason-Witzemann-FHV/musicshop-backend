package at.fhv.ae.shared.dto.sale;

import lombok.Value;

import java.io.Serializable;

@Value
public class SaleSearchResultRemoteDTO implements Serializable {
    String saleId;

    // TODO: if sale number is only a number, change this to int
    String saleNumber;
    String dateOfSale;
    String customerId;
    double totalPrice;
}
