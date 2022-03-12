package at.fhv.ae.backend.domain.model.sale;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
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
    @GeneratedValue()
    @Getter(AccessLevel.NONE)
    private Long saleIdInternal;

    @Embedded
    private SaleId saleId;

    private String employeeId;

    private String customerId;

    private PaymentType paymentType;

    private SaleType saleType;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<Item> items;

    public Sale() {

    }

    public Sale(Long saleIdInternal, SaleId saleId, String employeeId, String customerId, PaymentType paymentType, SaleType saleType, List<Item> items) {
        this.saleIdInternal = saleIdInternal;
        this.saleId = saleId;
        this.employeeId = employeeId;
        this.customerId = customerId;
        this.paymentType = paymentType;
        this.saleType = saleType;
        this.items = items;
    }

    public List<Item> items() {
        return Collections.unmodifiableList(this.items);
    }
}
