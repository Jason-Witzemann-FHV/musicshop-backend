package at.fhv.ae.backend.domain.model.sale;

import at.fhv.ae.backend.domain.model.user.UserId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Sale {

    @Id
    @GeneratedValue()
    @Getter(AccessLevel.NONE)
    private Long saleIdInternal;

    @Embedded
    private SaleId saleId;

    private UserId employeeId;

    private UserId customerId;

    private PaymentType paymentType;

    private SaleType saleType;

    private double totalPrice;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<Item> items;

    private Sale(SaleId saleId, UserId employeeId, UserId customerId, PaymentType paymentType, SaleType saleType, double totalPrice, List<Item> items) {
        this.saleId = saleId;
        this.employeeId = employeeId;
        this.customerId = customerId;
        this.paymentType = paymentType;
        this.saleType = saleType;
        this.totalPrice = totalPrice;
        this.items = items;
    }

    public List<Item> items() {
        return Collections.unmodifiableList(this.items);
    }

    public static Sale create(SaleId saleId, UserId employeeId, UserId customerId, PaymentType paymentType, SaleType saleType, List<Item> items) {
       return new Sale(
                saleId,
                employeeId,
                customerId,
                paymentType,
                saleType,
                items.stream().mapToDouble(Item::price).sum(),
                items
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sale sale = (Sale) o;

        return saleId.equals(sale.saleId);
    }

    @Override
    public int hashCode() {
        return saleId.hashCode();
    }
}
