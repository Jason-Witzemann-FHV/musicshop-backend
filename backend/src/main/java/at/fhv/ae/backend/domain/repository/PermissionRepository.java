package at.fhv.ae.backend.domain.repository;

import at.fhv.ae.backend.domain.model.permissions.Employee;
import at.fhv.ae.backend.domain.model.permissions.EmployeeId;

import java.util.Optional;


public interface PermissionRepository {

    Optional<Employee> employeeById(EmployeeId employeeId);

}
