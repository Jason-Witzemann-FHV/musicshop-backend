package at.fhv.ae.backend.domain.model.sale;

import lombok.Value;

import javax.persistence.Embeddable;
import java.util.UUID;

@Value
@Embeddable
public class SaleId {

    UUID id;

    // called by Hibernate
    @SuppressWarnings("ProtectedMemberInFinalClass")
    protected SaleId() {
        this.id = null;
    }
}
