package at.fhv.ae.backend.infrastructure;

import at.fhv.ae.backend.domain.model.user.User;
import at.fhv.ae.backend.domain.model.user.UserId;
import at.fhv.ae.backend.domain.repository.UserRepository;
import lombok.AllArgsConstructor;

import javax.persistence.EntityManager;
import java.util.Optional;

@AllArgsConstructor
public class HibernateUserRepository implements UserRepository {

    private EntityManager em;

    @Override
    public Optional<User> userById(UserId userId) {
        return em.createQuery("select u from User u where u.userId = :userId", User.class)
                .setParameter("userId", userId)
                .getResultList()
                .stream()
                .findFirst();
    }
}