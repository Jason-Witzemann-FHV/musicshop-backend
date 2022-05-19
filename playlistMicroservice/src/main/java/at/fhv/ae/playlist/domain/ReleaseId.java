package at.fhv.ae.playlist.domain;

import org.hibernate.annotations.Type;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

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

    @Override
    public String toString() {
        return id.toString();
    }
}
