package at.fhv.ae.shared.dto.sale;

import lombok.Value;

import java.io.Serializable;

@Value
public class ItemRemoteDTO implements Serializable {
    String title;
    int amount;
    double pricePerItem;
}
