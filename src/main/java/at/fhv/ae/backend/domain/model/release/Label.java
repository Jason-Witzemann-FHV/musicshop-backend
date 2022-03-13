package at.fhv.ae.backend.domain.model.release;

import lombok.AccessLevel;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class Label {

    @Id
    @GeneratedValue()
    @Getter(AccessLevel.NONE)
    private Long labelIdInternal;

    private String name;

    private String code;

    protected Label() {

    }

    public Label(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
