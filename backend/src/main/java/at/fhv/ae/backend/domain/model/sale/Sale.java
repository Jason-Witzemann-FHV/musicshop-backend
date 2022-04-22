package at.fhv.ae.backend.domain.model.sale;

import at.fhv.ae.backend.domain.model.user.UserId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bson.types.ObjectId;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "name", column = @Column(name = "employee_id")) })
    private UserId employeeId;

    private ObjectId customerId;

    private LocalDateTime sellTimestamp;

    private PaymentType paymentType;

    private SaleType saleType;

    private double totalPrice;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<Item> items;

    private Sale(SaleId saleId, UserId employeeId, ObjectId customerId, LocalDateTime sellTimestamp, PaymentType paymentType, SaleType saleType, double totalPrice, List<Item> items) {
        this.saleId = saleId;
        this.employeeId = employeeId;
        this.customerId = customerId;
        this.sellTimestamp = sellTimestamp;
        this.paymentType = paymentType;
        this.saleType = saleType;
        this.totalPrice = totalPrice;
        this.items = items;
    }

    public List<Item> items() {
        return Collections.unmodifiableList(this.items);
    }

    public static Sale create(SaleId saleId, UserId employeeId, ObjectId customerId, PaymentType paymentType, SaleType saleType, List<Item> items) {
       return new Sale(
                saleId,
                employeeId,
                customerId,
                LocalDateTime.now(),
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
