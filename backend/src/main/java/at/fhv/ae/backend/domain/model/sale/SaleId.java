package at.fhv.ae.backend.domain.model.sale;

import lombok.Value;
import org.hibernate.annotations.Type;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Value
@Embeddable
public class SaleId implements Serializable {

    int id;

    // called by Hibernate
    @SuppressWarnings("ProtectedMemberInFinalClass")
    protected SaleId() {
        this.id = -1;
    }

    public SaleId(int id) {
        this.id = id;
    }


}
