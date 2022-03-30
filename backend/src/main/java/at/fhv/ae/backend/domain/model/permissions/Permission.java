package at.fhv.ae.backend.domain.model.permissions;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Permission {

    @Id
    @GeneratedValue()
    @Getter(AccessLevel.NONE)
    private Long permissionIdInternal;

    private String permission;

    public Permission(String permission) {
        this.permission = permission;
        this.permissionIdInternal = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Permission that = (Permission) o;

        return permission.equals(that.permission);
    }

    @Override
    public int hashCode() {
        return permission.hashCode();
    }
}
