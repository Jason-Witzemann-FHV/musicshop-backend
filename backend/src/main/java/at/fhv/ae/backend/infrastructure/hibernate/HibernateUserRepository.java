package at.fhv.ae.backend.infrastructure.hibernate;

import at.fhv.ae.backend.domain.model.user.User;
import at.fhv.ae.backend.domain.model.user.UserId;
import at.fhv.ae.backend.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Stateless
@NoArgsConstructor
@AllArgsConstructor
public class HibernateUserRepository implements UserRepository {

    @PersistenceContext
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
