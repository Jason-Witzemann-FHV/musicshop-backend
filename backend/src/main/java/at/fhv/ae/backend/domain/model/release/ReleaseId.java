package at.fhv.ae.backend.domain.model.release;

import lombok.Value;
import org.hibernate.annotations.Type;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Value
@Embeddable
public class ReleaseId implements Serializable {

    @Type(type = "uuid-char")
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
