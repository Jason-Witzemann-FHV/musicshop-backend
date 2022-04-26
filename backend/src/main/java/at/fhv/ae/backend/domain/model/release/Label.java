package at.fhv.ae.backend.domain.model.release;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Label {

    @Id
    @GeneratedValue()
    @Getter(AccessLevel.NONE)
    private Long labelIdInternal;

    private String name;

    private String code;

    public Label(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
