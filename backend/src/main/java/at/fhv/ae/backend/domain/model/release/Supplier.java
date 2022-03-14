package at.fhv.ae.backend.domain.model.release;

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
public class Supplier {

    @Id
    @GeneratedValue
    @Getter(AccessLevel.NONE)
    private long supplierIdInternal;

    private String name;

    private String address;

    public Supplier(String name, String address) {
        this.address = address;
        this.name = name;
    }
}
