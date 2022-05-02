package at.fhv.ae.shared.dto.sale;

import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

@Value
public class ItemRemoteDTO implements Serializable {
    UUID itemId;
    String title;
    int amount;
    double pricePerItem;
    int numberOfReturnedItems;
}
