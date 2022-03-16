package at.fhv.ae.backend.domain.model.release;


import at.fhv.ae.backend.domain.model.work.RecordingId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Release {

    @Id
    @GeneratedValue
    @Getter(AccessLevel.NONE)
    private long releaseIdInternal;

    @Embedded
    private ReleaseId releaseId;

    private int stock;

    private String title;

    private Medium medium;

    @ManyToOne(cascade = {CascadeType.ALL})
    private Label label;

    @ManyToMany(cascade = {CascadeType.ALL})
    private List<Supplier> suppliers;

    public Release(ReleaseId releaseId, int stock, String title, Medium medium, Label label, List<Supplier> suppliers) {
        this.releaseId = releaseId;
        this.stock = stock;
        this.title = title;
        this.medium = medium;
        this.label = label;
        this.suppliers = suppliers;
    }

    public List<Supplier> suppliers() {
        return Collections.unmodifiableList(suppliers);
    }
}
