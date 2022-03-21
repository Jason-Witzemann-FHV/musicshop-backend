package at.fhv.ae.shared.dto.basket;

import lombok.Value;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.UUID;

@Value
@Accessors(fluent = true)
public class BasketItemRemoteDTO implements Serializable {
    UUID releaseId;
    String title;
    int quantity;
    String medium;
    double price;
}