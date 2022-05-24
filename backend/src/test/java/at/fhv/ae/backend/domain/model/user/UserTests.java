package at.fhv.ae.backend.domain.model.user;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTests {

    @Test
    void _without_duplicate_perms_when_get_all_perms_then_return_all() {
        var roleOperator = new Role("operator", Set.of(Permission.ORDER_RELEASES, Permission.PUBLISH_WEBFEED));
        var roleEmployee = new Role("employee", Set.of(Permission.SEARCH_RELEASES, Permission.SELL_RELEASES));
        var roles = Set.of(roleEmployee, roleOperator);
        var user = new User(new UserId("Jason"), roles, null, null);

        var expected = 4;
        var actual = user.permissions().size();
        assertEquals(expected, actual);
    }

    @Test
    void given_user_with_duplicate_perms_when_get_all_perms_then_return_all() {
        var roleOperator = new Role("operator", Set.of(Permission.ORDER_RELEASES, Permission.PUBLISH_WEBFEED));
        var roleEmployee = new Role("employee", Set.of(Permission.SEARCH_RELEASES, Permission.SELL_RELEASES));
        var roleCustomer = new Role("employee", Set.of(Permission.SEARCH_RELEASES, Permission.BUY_RELEASES));

        var roles = Set.of(roleEmployee, roleOperator, roleCustomer);
        var user = new User(new UserId("Jason"), roles, null, null);

        var expected = 5;
        var actual = user.permissions().size();
        assertEquals(expected, actual);
    }

    @Test
    void given_users_when_has_perm_on_set_perm_then_return_true() {
        var roleOperator = new Role("operator", Set.of(Permission.ORDER_RELEASES, Permission.PUBLISH_WEBFEED));
        var roleEmployee = new Role("employee", Set.of(Permission.SEARCH_RELEASES, Permission.SELL_RELEASES));
        var roles = Set.of(roleEmployee, roleOperator);
        var user = new User(new UserId("Jason"), roles, null, null);

        assertTrue(user.hasPermission(Permission.SEARCH_RELEASES));
    }

    @Test
    void given_users_when_has_perm_on_unset_perm_then_return_true() {
        var roleOperator = new Role("operator", Set.of(Permission.ORDER_RELEASES, Permission.PUBLISH_WEBFEED));
        var user = new User(new UserId("Jason"), Set.of(roleOperator), null, null);

        assertFalse(user.hasPermission(Permission.SELL_RELEASES));
    }

    @Test
    void given_user_with_topics_when_subscribed_check_subscription() {
        var topics = Set.of(SubscriptionTopics.SYSTEM_TOPIC, SubscriptionTopics.POP_TOPIC);
        var user = new User(new UserId("Jason"), null, topics, null);

        assertTrue(user.subscribedTo("SystemTopic"));
        assertTrue(user.subscribedTo("PopTopic"));
        assertFalse(user.subscribedTo("RockTopic"));

    }
}
