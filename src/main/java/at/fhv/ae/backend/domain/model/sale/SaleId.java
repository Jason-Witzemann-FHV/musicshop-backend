package at.fhv.ae.backend.domain.model.sale;

import lombok.AllArgsConstructor;
import lombok.Value;

import javax.persistence.Embeddable;
import java.util.UUID;

@Value
@Embeddable
@AllArgsConstructor
public class SaleId {

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
