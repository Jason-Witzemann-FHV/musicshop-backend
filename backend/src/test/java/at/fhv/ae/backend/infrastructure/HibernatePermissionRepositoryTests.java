package at.fhv.ae.backend.infrastructure;

import at.fhv.ae.backend.ServiceRegistry;
import at.fhv.ae.backend.domain.model.user.Permission;
import at.fhv.ae.backend.domain.model.user.Role;
import at.fhv.ae.backend.domain.model.user.User;
import at.fhv.ae.backend.domain.model.user.UserId;
import at.fhv.ae.backend.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HibernatePermissionRepositoryTests {

    private final EntityManager em = ServiceRegistry.entityManager();

    private final UserRepository userRepository = ServiceRegistry.permissionRepository();


    @Test
    void given_id_when_search_for_existing_empl_then_return_it() {
        var roleOperator = new Role("operator", Set.of(Permission.ORDER_RELEASES, Permission.PUBLISH_WEBFEED));
        var roleEmployee = new Role("employee", Set.of(Permission.SEARCH_RELEASES, Permission.SELL_RELEASES));
        var roles = Set.of(roleEmployee, roleOperator);
        var user = new User(new UserId("Jason"), roles);

        var transaction = em.getTransaction();
        transaction.begin();
        em.persist(user);
        em.flush();

        var actual = userRepository.userById(user.userId());
        transaction.rollback();

        assertTrue(actual.isPresent());
        assertEquals(user, actual.get());
    }


    @Test
    void given_id_when_search_for_not_existing_empl_then_return_it() {
        var id = new UserId("Jason");
        var transaction = em.getTransaction();
        transaction.begin();
        em.flush();

        var actual = userRepository.userById(id);
        transaction.rollback();

        assertTrue(actual.isEmpty());
    }


}
