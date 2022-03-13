package at.fhv.ae.backend.domain.model.work;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@ToString
@Getter
public class Artist {

    @Id
    @GeneratedValue
    @Getter(AccessLevel.NONE)
    private Long artistIdInternal;

    private String name;

    protected Artist() { } // called by Hibernate

    public Artist(String name) {
        this.name = name;
    }

}
