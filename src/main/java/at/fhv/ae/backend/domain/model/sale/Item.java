package at.fhv.ae.backend.domain.model.sale;


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

}
