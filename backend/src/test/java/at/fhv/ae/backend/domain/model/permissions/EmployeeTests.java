package at.fhv.ae.backend.domain.model.permissions;

import org.junit.jupiter.api.Test;

import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
class EmployeeTests {

    @Test
    void given_employee_without_duplicate_perms_when_get_all_perms_then_return_all() {
        var roleOperator = new Role("operator", Set.of(new Permission("publish_webfeed"), new Permission("order_releases")));
        var roleEmployee = new Role("employee", Set.of(new Permission("search_releases"), new Permission("sell_releases")));
        var roles = Set.of(roleEmployee, roleOperator);
        var employee = new Employee(new EmployeeId("Jason"), roles);

        var expected = 4;
        var actual = employee.permissions().size();
        assertEquals(expected, actual);
    }

    @Test
    void given_employee_with_duplicate_perms_when_get_all_perms_then_return_all() {
        var roleOperator = new Role("operator", Set.of(new Permission("publish_webfeed"), new Permission("order_releases")));
        var roleEmployee = new Role("employee", Set.of(new Permission("search_releases"), new Permission("sell_releases")));
        var roleCustomer = new Role("customer", Set.of(new Permission("search_releases")));
        var roles = Set.of(roleEmployee, roleOperator, roleCustomer);
        var employee = new Employee(new EmployeeId("Jason"), roles);

        var expected = 4;
        var actual = employee.permissions().size();
        assertEquals(expected, actual);
    }

    @Test
    void given_employees_when_has_perm_on_set_perm_then_return_true() {
        var roleOperator = new Role("operator", Set.of(new Permission("publish_webfeed"), new Permission("order_releases")));
        var roleEmployee = new Role("operator", Set.of(new Permission("search_releases"), new Permission("sell_releases")));
        var roles = Set.of(roleEmployee, roleOperator);
        var employee = new Employee(new EmployeeId("Jason"), roles);

        assertTrue(employee.hasPermission(new Permission("search_releases")));
    }

    @Test
    void given_employees_when_has_perm_on_unset_perm_then_return_true() {
        var roleOperator = new Role("operator", Set.of(new Permission("publish_webfeed"), new Permission("order_releases")));
        var roleEmployee = new Role("operator", Set.of(new Permission("search_releases"), new Permission("sell_releases")));
        var roles = Set.of(roleEmployee, roleOperator);
        var employee = new Employee(new EmployeeId("Jason"), roles);

        assertFalse(employee.hasPermission(new Permission("fire_employee")));
    }

}
