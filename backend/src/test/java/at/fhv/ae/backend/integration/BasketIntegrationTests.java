package at.fhv.ae.backend.integration;

import at.fhv.ae.backend.ServiceRegistry;
import at.fhv.ae.backend.domain.model.release.*;
import at.fhv.ae.backend.domain.model.user.Permission;
import at.fhv.ae.backend.domain.model.user.Role;
import at.fhv.ae.backend.domain.model.user.User;
import at.fhv.ae.backend.domain.model.user.UserId;
import at.fhv.ae.backend.domain.model.work.RecordingId;
import at.fhv.ae.backend.middleware.rmi.services.RemoteBasketServiceImpl;
import at.fhv.ae.shared.dto.basket.BasketItemRemoteDTO;
import at.fhv.ae.shared.rmi.RemoteBasketService;
import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;


import static org.junit.jupiter.api.Assertions.*;

class BasketIntegrationTests {

    private static RemoteBasketService remoteBasketService;

    private final EntityManager entityManager = ServiceRegistry.entityManager();

    // Domain objects that will be persisted since we have no good way of transaction-handling in tests
    // After each test, they will be removed from the Database
    private List<Object> domainObjects;

    @BeforeAll
    static void setup() {
        try {
            LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            Naming.rebind("rmi://localhost/basket-service", new RemoteBasketServiceImpl("nsu3146", ServiceRegistry.basketService()));
            remoteBasketService = (RemoteBasketService) Naming.lookup("rmi://localhost/basket-service");
            ServiceRegistry.basketRepository().clearBasket(new UserId("nsu3146")); // basket might have data in it from other tests
        } catch (Exception e) {
            Assumptions.assumeTrue(false, "Setup of Integration-Test failed, skipping Test!");
        }
    }

    @AfterEach
    void tearDown() {
        // clear basket repository since it is not handled by transactions
        ServiceRegistry.basketRepository().clearBasket(new UserId("nsu3146"));

        // remove test data of test from database
        var transaction = entityManager.getTransaction();
        transaction.begin();
        domainObjects.forEach(entityManager::remove);
        transaction.commit();
        domainObjects = List.of();
    }

    @Test
    void given_empty_basket_when_add_three_items_to_basket_then_return_all_remote_dtos() throws RemoteException {

        //prepare Testdata
        var userId = new UserId("nsu3146");
        var role = new Role("Seller", Set.of(Permission.SELL_RELEASES, Permission.SEARCH_RELEASES));
        var user = new User(userId, Set.of(role), null);

        var label = new Label("Test-Label", "TST");
        var supplier = new Supplier("Test-Supplier", "Teststraße 44, 3231 Testhausen, Österreich");
        var release1 = new Release(new ReleaseId(UUID.randomUUID()), 100, "Test-Release 1", Medium.CD, 23.21, label, List.of(supplier), List.of(new RecordingId(UUID.randomUUID())));
        var release2 = new Release(new ReleaseId(UUID.randomUUID()), 100, "Test-Release 2", Medium.CD, 23.21, label, List.of(supplier), List.of(new RecordingId(UUID.randomUUID())));
        var release3 = new Release(new ReleaseId(UUID.randomUUID()), 100, "Test-Release 3", Medium.CD, 23.21, label, List.of(supplier), List.of(new RecordingId(UUID.randomUUID())));

        domainObjects = List.of(label, supplier, release1, release2, release3, role, user); // domain objects for tests
        var releases = List.of(Map.entry(release1, 1), Map.entry(release2, 2), Map.entry(release3, 3));

        // persist testdata
        var transaction = entityManager.getTransaction();
        transaction.begin();
        domainObjects.forEach(entityManager::persist);
        transaction.commit();

        // save Data here
        for (var entry : releases) {
            remoteBasketService.addItemToBasket(entry.getKey().releaseId().id(), entry.getValue());
        }

        // load data here. Must be a Map since the right order is not guaranteed.
        var remoteDTOs = remoteBasketService.itemsInBasket().stream().collect(Collectors.toMap(BasketItemRemoteDTO::getReleaseId, Function.identity()));

        //validate
        assertEquals(releases.size(), remoteDTOs.size());
        for (var entry : releases) {
            var release = entry.getKey();
            assertTrue(remoteDTOs.containsKey(release.releaseId().id()));
            var remote = remoteDTOs.get(release.releaseId().id());
            assertEquals(release.title(), remote.getTitle());
            assertEquals(entry.getValue(), remote.getQuantity());
            assertEquals(release.medium().friendlyName(), remote.getMedium());
            assertEquals(release.price(), remote.getPrice());
        }
    }

    @Test
    void given_empty_basket_when_add_three_items_to_basket_then_return_amount_of_items() throws RemoteException {

        //prepare Testdata
        var userId = new UserId("nsu3146");
        var role = new Role("Seller", Set.of(Permission.SELL_RELEASES, Permission.SEARCH_RELEASES));
        var user = new User(userId, Set.of(role), null);

        var label = new Label("Test-Label", "TST");
        var supplier = new Supplier("Test-Supplier", "Teststraße 44, 3231 Testhausen, Österreich");
        var release1 = new Release(new ReleaseId(UUID.randomUUID()), 100, "Test-Release 1", Medium.CD, 23.21, label, List.of(supplier), List.of(new RecordingId(UUID.randomUUID())));
        var release2 = new Release(new ReleaseId(UUID.randomUUID()), 100, "Test-Release 2", Medium.CD, 23.21, label, List.of(supplier), List.of(new RecordingId(UUID.randomUUID())));
        var release3 = new Release(new ReleaseId(UUID.randomUUID()), 100, "Test-Release 3", Medium.CD, 23.21, label, List.of(supplier), List.of(new RecordingId(UUID.randomUUID())));

        domainObjects = List.of(label, supplier, release1, release2, release3, user, role); // domain objects for tests
        var releases = List.of(Map.entry(release1, 1), Map.entry(release2, 2), Map.entry(release3, 3));

        // persist testdata
        var transaction = entityManager.getTransaction();
        transaction.begin();
        domainObjects.forEach(entityManager::persist);
        transaction.commit();

        // save Data here
        for (var entry : releases) {
            remoteBasketService.addItemToBasket(entry.getKey().releaseId().id(), entry.getValue());
        }

        // load data here
        var amountOfItemsInBasket = remoteBasketService.amountOfItemsInBasket();

        // validate
        assertEquals(releases.stream().mapToInt(Map.Entry::getValue).sum(), amountOfItemsInBasket);
    }


    @Test
    void given_empty_basket_when_add_three_items_with_quantity_one_to_basket_and_increment_quantity_of_one_then_return_new_amount_of_items() throws RemoteException {

        //prepare Testdata
        var userId = new UserId("nsu3146");
        var role = new Role("Seller", Set.of(Permission.SELL_RELEASES, Permission.SEARCH_RELEASES));
        var user = new User(userId, Set.of(role), null);

        var label = new Label("Test-Label", "TST");
        var supplier = new Supplier("Test-Supplier", "Teststraße 44, 3231 Testhausen, Österreich");
        var release1 = new Release(new ReleaseId(UUID.randomUUID()), 100, "Test-Release 1", Medium.CD, 23.21, label, List.of(supplier), List.of(new RecordingId(UUID.randomUUID())));
        var release2 = new Release(new ReleaseId(UUID.randomUUID()), 100, "Test-Release 2", Medium.CD, 23.21, label, List.of(supplier), List.of(new RecordingId(UUID.randomUUID())));
        var release3 = new Release(new ReleaseId(UUID.randomUUID()), 100, "Test-Release 3", Medium.CD, 23.21, label, List.of(supplier), List.of(new RecordingId(UUID.randomUUID())));

        domainObjects = List.of(label, supplier, release1, release2, release3, role, user); // domain objects for tests
        var releases = List.of(Map.entry(release1, 1), Map.entry(release2, 1), Map.entry(release3, 1));

        // persist testdata
        var transaction = entityManager.getTransaction();
        transaction.begin();
        domainObjects.forEach(entityManager::persist);
        transaction.commit();

        // save Data here
        for (var entry : releases) {
            remoteBasketService.addItemToBasket(entry.getKey().releaseId().id(), entry.getValue());
        }
        remoteBasketService.changeQuantityOfItem(releases.get(0).getKey().releaseId().id(), releases.get(0).getValue() + 1);

        // load data here
        var amountOfItemsInBasket = remoteBasketService.amountOfItemsInBasket();

        // validate
        assertEquals(releases.stream().mapToInt(Map.Entry::getValue).sum() + 1, amountOfItemsInBasket);

    }

    @Test
    void given_basket_with_three_items_when_remove_one_then_return_remaining_two() throws RemoteException {

        //prepare Testdata
        var userId = new UserId("nsu3146");
        var role = new Role("Seller", Set.of(Permission.SELL_RELEASES, Permission.SEARCH_RELEASES));
        var user = new User(userId, Set.of(role), null);

        var label = new Label("Test-Label", "TST");
        var supplier = new Supplier("Test-Supplier", "Teststraße 44, 3231 Testhausen, Österreich");
        var release1 = new Release(new ReleaseId(UUID.randomUUID()), 100, "Test-Release 1", Medium.CD, 23.21, label, List.of(supplier), List.of(new RecordingId(UUID.randomUUID())));
        var release2 = new Release(new ReleaseId(UUID.randomUUID()), 100, "Test-Release 2", Medium.CD, 23.21, label, List.of(supplier), List.of(new RecordingId(UUID.randomUUID())));
        var release3 = new Release(new ReleaseId(UUID.randomUUID()), 100, "Test-Release 3", Medium.CD, 23.21, label, List.of(supplier), List.of(new RecordingId(UUID.randomUUID())));

        domainObjects = List.of(label, supplier, release1, release2, release3, role, user); // domain objects for tests
        var releases = List.of(Map.entry(release1, 1), Map.entry(release2, 2), Map.entry(release3, 3));

        // persist testdata
        var transaction = entityManager.getTransaction();
        transaction.begin();
        domainObjects.forEach(entityManager::persist);
        transaction.commit();

        // save Data here
        for (var entry : releases) {
            remoteBasketService.addItemToBasket(entry.getKey().releaseId().id(), entry.getValue());
        }

        remoteBasketService.removeItemFromBasket(release2.releaseId().id()); // remove second DTO
        var remoteDTOs = remoteBasketService.itemsInBasket().stream().collect(Collectors.toMap(BasketItemRemoteDTO::getReleaseId, Function.identity())); // returned list has no sort order, therefore use map

        var expectedReleases = List.of(Map.entry(release1, 1), Map.entry(release3, 3));
        //validate
        assertEquals(expectedReleases.size(), remoteDTOs.size());
        for (var entry : expectedReleases) {
            var release = entry.getKey();
            assertTrue(remoteDTOs.containsKey(release.releaseId().id()));
            var remote = remoteDTOs.get(release.releaseId().id());
            assertEquals(release.title(), remote.getTitle());
            assertEquals(entry.getValue(), remote.getQuantity());
            assertEquals(release.medium().friendlyName(), remote.getMedium());
            assertEquals(release.price(), remote.getPrice());
        }
    }

}
