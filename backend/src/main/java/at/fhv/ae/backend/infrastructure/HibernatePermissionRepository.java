package at.fhv.ae.backend.infrastructure;

import at.fhv.ae.backend.domain.model.permissions.Employee;
import at.fhv.ae.backend.domain.model.permissions.EmployeeId;
import at.fhv.ae.backend.domain.repository.PermissionRepository;
import lombok.AllArgsConstructor;

import javax.persistence.EntityManager;
import java.util.Optional;

@AllArgsConstructor
public class HibernatePermissionRepository implements PermissionRepository {

    private EntityManager em;

    @Override
    public Optional<Employee> employeeById(EmployeeId employeeId) {
        return em.createQuery("select r from Employee r where r.employeeId = :employeeId", Employee.class)
                .setParameter("employeeId", employeeId)
                .getResultList()
                .stream()
                .findFirst();
    }
}
