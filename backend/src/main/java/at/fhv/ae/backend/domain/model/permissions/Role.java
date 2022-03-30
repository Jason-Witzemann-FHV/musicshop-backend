package at.fhv.ae.backend.domain.model.permissions;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collections;
import java.util.Set;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role {

    @Id
    @GeneratedValue()
    @Getter(AccessLevel.NONE)
    private Long roleIdInternal;

    private String roleName;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    private Set<Permission> permissions;

    public Role(String roleName, Set<Permission> permissions) {
        this.roleName = roleName;
        this.permissions = permissions;
    }

    public Set<Permission> permissions() {
        return Collections.unmodifiableSet(permissions);
    }
}
