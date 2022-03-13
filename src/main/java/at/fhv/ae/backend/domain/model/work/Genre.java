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
public class Genre {

    @Id
    @GeneratedValue
    @Getter(AccessLevel.NONE)
    private Long genreIdInternal;

    private String name;

    protected Genre() { } // called by Hibernate

    public Genre(String name) {
        this.name = name;
    }

}
