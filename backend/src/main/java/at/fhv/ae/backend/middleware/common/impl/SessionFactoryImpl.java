package at.fhv.ae.backend.middleware.common.impl;

import at.fhv.ae.backend.domain.model.permissions.Employee;
import at.fhv.ae.backend.domain.model.permissions.EmployeeId;
import at.fhv.ae.backend.domain.repository.PermissionRepository;
import at.fhv.ae.backend.middleware.common.AuthorizationService;
import at.fhv.ae.backend.middleware.common.Session;
import at.fhv.ae.backend.middleware.common.SessionFactory;
import java.util.Optional;

public class SessionFactoryImpl implements SessionFactory {
    private final AuthorizationService authorizationService;
    private final PermissionRepository permissionRepository;


    public SessionFactoryImpl(AuthorizationService authorizationService, PermissionRepository permissionRepository) {
        this.authorizationService = authorizationService;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public Optional<Session> logIn(String username, String password) {
        if (!authorizationService.authorize(username, password)) {
            return Optional.empty();
        }

        Optional<Employee> user = permissionRepository.employeeById(new EmployeeId(username));

        if (user.isEmpty()){
            System.out.println("Found User in AuthService, but not in Repository!");
            return Optional.empty();
        }

        Employee employee = user.get();

        return Optional.of(new SessionImpl(employee));
    }

}
