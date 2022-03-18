package at.fhv.ae.backend.domain.model.work;


import lombok.Value;

import javax.persistence.Embeddable;
import java.util.UUID;

@Value
@Embeddable
public class RecordingId {

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
