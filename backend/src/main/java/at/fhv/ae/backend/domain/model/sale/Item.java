package at.fhv.ae.backend.domain.model.sale;

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

    public Item(ReleaseId releaseId, int amount, double price) {
        this.releaseId = releaseId;
        this.amount = amount;
        this.price = price;
    }
}
