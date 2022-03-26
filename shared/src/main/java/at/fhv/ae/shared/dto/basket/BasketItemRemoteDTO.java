package at.fhv.ae.shared.dto.basket;

import lombok.Value;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.UUID;

@Value
public class BasketItemRemoteDTO implements Serializable {
    UUID releaseId;
    String title;
    int quantity;
    int stock;
    String medium;
    double price;
}