package at.fhv.ae.backend.domain.model.work;


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
public class Artist {

    @Id
    @GeneratedValue
    @Getter(AccessLevel.NONE)
    private Long artistIdInternal;

    private String name;

    public Artist(String name) {
        this.name = name;
    }
}
