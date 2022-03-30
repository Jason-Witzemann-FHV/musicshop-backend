package at.fhv.ae.backend.domain.model.user;

import lombok.Value;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Value
@Embeddable
public class UserId implements Serializable {

    String name;

    protected UserId() {
        this.name = null;
    }

    public UserId(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
