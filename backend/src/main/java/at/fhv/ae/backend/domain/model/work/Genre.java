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
public class Genre {

    @Id
    @GeneratedValue
    @Getter(AccessLevel.NONE)
    private Long genreIdInternal;

    private String name;

    public Genre(String name) {
        this.name = name;
    }
}
