package at.fhv.ae.backend.application.dto;

import at.fhv.ae.backend.domain.model.release.Release;
import at.fhv.ae.backend.domain.model.sale.Item;
import lombok.Value;

import java.util.UUID;

@Value
public class ItemDTO {
    UUID itemId;
    String title;
    int amount;
    double pricePerItem;
    int numberOfReturnedItems;

    public static ItemDTO fromDomain(Item item, Release release) {
        return new ItemDTO(
                release.releaseId().id(),
                release.title(),
                item.amount(),
                item.price(),
                item.nrOfReturnedItems()
        );
    }
}
