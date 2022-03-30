package at.fhv.ae.backend.middleware.common.impl;

import at.fhv.ae.backend.domain.model.permissions.Employee;
import at.fhv.ae.backend.middleware.common.Session;

public class SessionImpl implements Session {
    private final Employee employee;

    public SessionImpl(Employee employee) {
        this.employee = employee;
    }
}
