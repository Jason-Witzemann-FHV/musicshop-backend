package at.fhv.ae.backend.infrastructure;

import at.fhv.ae.backend.ServiceRegistry;
import at.fhv.ae.backend.domain.model.permissions.Employee;
import at.fhv.ae.backend.domain.model.permissions.EmployeeId;
import at.fhv.ae.backend.domain.model.permissions.Permission;
import at.fhv.ae.backend.domain.model.permissions.Role;
import at.fhv.ae.backend.domain.repository.PermissionRepository;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class HibernatePermissionRepositoryTests {

    private final EntityManager em = ServiceRegistry.entityManager();

    private final PermissionRepository permissionRepository = ServiceRegistry.permissionRepository();


    @Test
    void given_id_when_search_for_existing_empl_then_return_it() {
        var roleOperator = new Role("operator", Set.of(new Permission("publish_webfeed"), new Permission("order_releases")));
        var roleEmployee = new Role("employee", Set.of(new Permission("search_releases"), new Permission("sell_releases")));
        var roles = Set.of(roleEmployee, roleOperator);
        var employee = new Employee(new EmployeeId("Jason"), roles);

        var transaction = em.getTransaction();
        transaction.begin();
        em.persist(employee);
        em.flush();

        var actual = permissionRepository.employeeById(employee.employeeId());
        transaction.rollback();

        assertTrue(actual.isPresent());
        assertEquals(employee, actual.get());
    }


    @Test
    void given_id_when_search_for_not_existing_empl_then_return_it() {
        var id = new EmployeeId("Jason");
        var transaction = em.getTransaction();
        transaction.begin();
        em.flush();

        var actual = permissionRepository.employeeById(id);
        transaction.rollback();

        assertTrue(actual.isEmpty());
    }


}
