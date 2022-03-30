package at.fhv.ae.backend.domain.model.permissions;

import lombok.Value;

import javax.persistence.Embeddable;

@Value
@Embeddable
public class EmployeeId {

    String name;

    protected EmployeeId() {
        this.name = null;
    }

    public EmployeeId(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
