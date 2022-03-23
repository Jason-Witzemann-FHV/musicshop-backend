package at.fhv.ae.backend.domain.model.sale;

import lombok.Value;
import org.hibernate.annotations.Type;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Value
@Embeddable
public class SaleId implements Serializable {

    @Type(type = "uuid-char")
    UUID id;

    // called by Hibernate
    @SuppressWarnings("ProtectedMemberInFinalClass")
    protected SaleId() {
        this.id = null;
    }

    public SaleId(UUID id) {
        this.id = id;
    }
}
