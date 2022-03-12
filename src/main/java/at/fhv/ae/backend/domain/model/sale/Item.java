package at.fhv.ae.backend.domain.model.sale;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Item {

    @Id
    @GeneratedValue
    private Long id;

    private String releaseId;

    private int amount;

    private double price;

    public Item() {
    }

    public Item(Long id, String releaseId, int amount, double price) {
        this.id = id;
        this.releaseId = releaseId;
        this.amount = amount;
        this.price = price;
    }
}
