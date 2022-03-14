package at.fhv.ae.backend.domain.model.sale;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

    private String releaseId;

    private int amount;

    private double price;

    public Item(String releaseId, int amount, double price) {
        this.releaseId = releaseId;
        this.amount = amount;
        this.price = price;
    }
}
