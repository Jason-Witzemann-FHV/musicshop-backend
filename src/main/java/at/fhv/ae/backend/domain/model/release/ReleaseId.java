package at.fhv.ae.backend.domain.model.release;

import lombok.Value;

import javax.persistence.Embeddable;
import java.util.UUID;

@Value
@Embeddable
public class ReleaseId {

    UUID id;

    // called by Hibernate
    @SuppressWarnings("ProtectedMemberInFinalClass")
    protected ReleaseId() {
        this.id = null;
    }

    public ReleaseId(UUID id) {
        this.id = id;
    }
}
