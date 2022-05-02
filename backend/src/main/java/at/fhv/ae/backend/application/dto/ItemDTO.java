package at.fhv.ae.backend.application.dto;

import at.fhv.ae.backend.domain.model.release.ReleaseId;
import lombok.Value;

import java.util.UUID;

@Value
public class ItemDTO {
    ReleaseId itemId;
    String title;
    int amount;
    double pricePerItem;
    int numberOfReturnedItems;
}
