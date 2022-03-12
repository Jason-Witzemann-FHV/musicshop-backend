package at.fhv.ae.backend.domain.model.sale;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@ToString
@Entity(name="sale")
@Getter
public class Sale {

    @Id
    @GeneratedValue
    @Getter(AccessLevel.NONE)
    private Long id;

    @Embedded
    private SaleId saleId;

    private String employeeId;

    private String customerId;

    private PaymentType paymentType;

    private SaleType saleType;

    @OneToMany
    private List<Item> items;

    public List<Item> items() {
        return Collections.unmodifiableList(this.items);
    }
}
