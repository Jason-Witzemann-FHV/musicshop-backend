package at.fhv.ae.backend.application.dto;

import lombok.Value;

@Value
public class ItemDTO {
    String title;
    int amount;
    double pricePerItem;
}
