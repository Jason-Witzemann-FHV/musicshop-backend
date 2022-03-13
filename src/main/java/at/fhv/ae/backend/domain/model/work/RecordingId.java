package at.fhv.ae.backend.domain.model.work;


import lombok.Getter;
import lombok.ToString;
import lombok.Value;

import javax.persistence.Embeddable;
import java.util.UUID;

@Value
@Embeddable
@ToString
@Getter
public class RecordingId {

    private UUID id;

    // called by Hibernate
    @SuppressWarnings("ProtectedMemberInFinalClass")
    protected RecordingId() {
        this.id = null;
    }

    public RecordingId(UUID id) {
        this.id = id;
    }
}
