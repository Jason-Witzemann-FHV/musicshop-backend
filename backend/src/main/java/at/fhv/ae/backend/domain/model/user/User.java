package at.fhv.ae.backend.domain.model.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bson.types.ObjectId;

import javax.persistence.*;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "`User`")
public class User {

    @Id
    @GeneratedValue
    @Getter(AccessLevel.NONE)
    private Long userIdInternal;

    @Embedded
    private UserId userId;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    private Set<Role> roles;

    @ElementCollection()
    @Enumerated(EnumType.STRING)
    private Set<SubscriptionTopics> subscriptionTopics;

    @Column(nullable = false)
    private ObjectId customerId;

    public User(UserId userId, Set<Role> roles, Set<SubscriptionTopics> subscriptionTopics, ObjectId customerId) {
        this.userId = userId;
        this.roles = roles;
        this.subscriptionTopics = subscriptionTopics;
        this.customerId = customerId;
    }

    public Set<Permission> permissions() {
        return roles.stream().flatMap(r -> r.permissions().stream()).collect(Collectors.toSet());
    }

    public boolean hasPermission(Permission permissionToCheck) {
        return permissions().stream().anyMatch(permissionToCheck::equals) || permissionToCheck == Permission.NONE;
    }

    public boolean subscribedTo(String topic) {
        return subscriptionTopics.stream().anyMatch(t ->  t.friendlyName().equalsIgnoreCase(topic));
    }

    public String name() {
        return userId.name();
    }

    public Set<Role> roles() {
        return Collections.unmodifiableSet(roles);
    }

    public Set<SubscriptionTopics> subscriptionTopics() { return Collections.unmodifiableSet(subscriptionTopics);}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return userId.equals(user.userId);
    }

    @Override
    public int hashCode() {
        return userId.hashCode();
    }
}
