package at.fhv.ae.backend.domain.model.work;


import lombok.Value;
import org.hibernate.annotations.Type;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Value
@Embeddable
public class RecordingId  implements Serializable {

    @Type(type = "uuid-char")
    UUID id;

    // called by Hibernate
    @SuppressWarnings("ProtectedMemberInFinalClass")
    protected RecordingId() {
        this.id = null;
    }

    public RecordingId(UUID id) {
        this.id = id;
    }
}
