package at.fhv.ae.backend.domain.model.work;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@ToString
public class Work {

    @Id
    @GeneratedValue
    @Getter(AccessLevel.NONE)
    private Long workIdInternal;

    private String title;

    protected Work() { } // called by Hibernate

    public Work(String title) {
        this.title = title;
    }

}
