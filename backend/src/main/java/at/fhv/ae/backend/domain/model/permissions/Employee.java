package at.fhv.ae.backend.domain.model.permissions;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Employee {

    @Id
    @GeneratedValue
    @Getter(AccessLevel.NONE)
    private long employeeIdInternal;

    @Embedded
    private EmployeeId employeeId;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    private Set<Role> roles;

    public Employee(EmployeeId employeeId, Set<Role> roles) {
        this.employeeId = employeeId;
        this.roles = roles;
    }

    public Set<Permission> permissions() {
        return roles.stream().flatMap(r -> r.permissions().stream()).collect(Collectors.toSet());
    }

    public boolean hasPermission(Permission permissionToCheck) {
        return permissions().stream().anyMatch(p -> p.equals(permissionToCheck));
    }

    public String name() {
        return employeeId.name();
    }

    public Set<Role> roles() {
        return Collections.unmodifiableSet(roles);
    }
}
