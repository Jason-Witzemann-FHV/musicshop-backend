package at.fhv.ae.backend.domain.model.sale;

import at.fhv.ae.backend.domain.model.release.Release;
import at.fhv.ae.backend.domain.model.release.ReleaseId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {

    @Id
    @GeneratedValue
    @Getter(AccessLevel.NONE)
    private Long itemIdInternal;

    @Embedded
    private ReleaseId releaseId;

    private int amount;

    private double price;

    private int nrOfReturnedItems;

    public Item(ReleaseId releaseId, int amount, double price) {
        this.releaseId = releaseId;
        this.amount = amount;
        this.price = price;
    }

    public void returnItems(int itemsToReturn) {
        if(itemsToReturn <= 0)
            throw new IllegalArgumentException("cannot return a number of items <= 0");

        int numberOfItemsRemaining = amount - nrOfReturnedItems;

        if (itemsToReturn > numberOfItemsRemaining)
            throw new IllegalArgumentException("cannot return this many items");

        nrOfReturnedItems += itemsToReturn;

    }
}
