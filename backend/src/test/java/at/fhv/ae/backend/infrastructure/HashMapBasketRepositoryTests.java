package at.fhv.ae.backend.infrastructure;

import at.fhv.ae.backend.domain.model.release.*;
import at.fhv.ae.backend.domain.model.user.UserId;
import at.fhv.ae.backend.domain.model.work.RecordingId;
import at.fhv.ae.backend.domain.repository.BasketRepository;
import at.fhv.ae.backend.infrastructure.inmemory.HashMapBasketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


class HashMapBasketRepositoryTests {

    private BasketRepository basketRepository = new HashMapBasketRepository();



    @BeforeEach
    void cleanBefore() {
        basketRepository.clearBasket(new UserId("nsu3146"));
    }

    @Test
    void given_no_items_in_basket_when_get_basket_items_in_basket_then_empty_map() {
        var user = new UserId("nsu3146");
        var basket = basketRepository.itemsInBasket(user);
        assertNotNull(basket);
        assertTrue(basket.isEmpty());
    }

    @Test
    void given_some_items_in_basket_when_get_basket_items_in_basket_then_not_empty_map() {
        var user = new UserId("nsu3146");
        var label = new Label("Test-Label", "TST");
        var supplier = new Supplier("Test-Supplier", "Teststraße 44, 3231 Testhausen, Österreich");
        var release = new Release(new ReleaseId(UUID.randomUUID()), 100, "Test-Release", Medium.CD, 23.21, label, List.of(supplier), List.of(new RecordingId(UUID.randomUUID())));
        basketRepository.addItemToBasket(user, release, 1);

        var basket = basketRepository.itemsInBasket(user);
        assertNotNull(basket);
        assertEquals(1, basket.size());

    }

    @Test
    void given_three_items_with_quantity_of_two_in_basket_when_get_amount_of_items_then_return_six() {
        var user = new UserId("nsu3146");
        var label = new Label("Test-Label", "TST");
        var supplier = new Supplier("Test-Supplier", "Teststraße 44, 3231 Testhausen, Österreich");
        var release1 = new Release(new ReleaseId(UUID.randomUUID()), 100, "Test-Release 1", Medium.CD, 23.21, label, List.of(supplier), List.of(new RecordingId(UUID.randomUUID())));
        var release2 = new Release(new ReleaseId(UUID.randomUUID()), 100, "Test-Release 2", Medium.CD, 23.21, label, List.of(supplier), List.of(new RecordingId(UUID.randomUUID())));
        var release3 = new Release(new ReleaseId(UUID.randomUUID()), 100, "Test-Release 3", Medium.CD, 23.21, label, List.of(supplier), List.of(new RecordingId(UUID.randomUUID())));
        basketRepository.addItemToBasket(user, release1, 2);
        basketRepository.addItemToBasket(user, release2, 2);
        basketRepository.addItemToBasket(user, release3, 2);

        var amountOfItemsInBasket = basketRepository.amountOfItemsInBasket(user);
        assertEquals(6, amountOfItemsInBasket);
    }


    @Test
    void given_two_items_in_basket_when_remove_one_item_then_other_item_is_only_one_in_basket() {
        var user = new UserId("nsu3146");
        var label = new Label("Test-Label", "TST");
        var supplier = new Supplier("Test-Supplier", "Teststraße 44, 3231 Testhausen, Österreich");
        var release1 = new Release(new ReleaseId(UUID.randomUUID()), 100, "Test-Release 1", Medium.CD, 23.21, label, List.of(supplier), List.of(new RecordingId(UUID.randomUUID())));
        var release2 = new Release(new ReleaseId(UUID.randomUUID()), 100, "Test-Release 2", Medium.CD, 23.21, label, List.of(supplier), List.of(new RecordingId(UUID.randomUUID())));
        basketRepository.addItemToBasket(user, release1, 1);
        basketRepository.addItemToBasket(user, release2,1);

        basketRepository.removeItemFromBasket(user, release1);

        var basket = basketRepository.itemsInBasket(user);
        assertEquals(1, basket.size());
        assertTrue(basket.containsKey(release2));
        assertFalse(basket.containsKey(release1));
    }

    @Test
    void given_immutable_basket_when_try_to_add_item_to_basket_object_then_error() {
        var user = new UserId("nsu3146");
        var basket = basketRepository.itemsInBasket(user);

        var label = new Label("Test-Label", "TST");
        var supplier = new Supplier("Test-Supplier", "Teststraße 44, 3231 Testhausen, Österreich");
        var release = new Release(new ReleaseId(UUID.randomUUID()), 100, "Test-Release 1", Medium.CD, 23.21, label, List.of(supplier), List.of(new RecordingId(UUID.randomUUID())));

        assertThrows(Exception.class, () -> basket.put(release, 3));
    }

    @Test
    void given_one_item_in_basket_when_try_remove_other_item_then_throw_illegal_state_exception() {
        var user = new UserId("nsu3146");
        var label = new Label("Test-Label", "TST");
        var supplier = new Supplier("Test-Supplier", "Teststraße 44, 3231 Testhausen, Österreich");
        var releaseInBasket = new Release(new ReleaseId(UUID.randomUUID()), 100, "Test-Release 1", Medium.CD, 23.21, label, List.of(supplier), List.of(new RecordingId(UUID.randomUUID())));
        basketRepository.addItemToBasket(user, releaseInBasket, 1);

        var releaseNotInBasket = new Release(new ReleaseId(UUID.randomUUID()), 100, "Test-Release 2", Medium.CD, 23.21, label, List.of(supplier), List.of(new RecordingId(UUID.randomUUID())));

        assertThrows(IllegalArgumentException.class, () -> basketRepository.removeItemFromBasket(user, releaseNotInBasket));

    }

    @Test
    void given_one_item_with_quantity_two_when_change_quantity_to_three_then_quantity_is_three() {
        var user = new UserId("nsu3146");
        var label = new Label("Test-Label", "TST");
        var supplier = new Supplier("Test-Supplier", "Teststraße 44, 3231 Testhausen, Österreich");
        var release = new Release(new ReleaseId(UUID.randomUUID()), 100, "Test-Release", Medium.CD, 23.21, label, List.of(supplier), List.of(new RecordingId(UUID.randomUUID())));
        basketRepository.addItemToBasket(user, release, 2);

        basketRepository.changeQuantityOfItem(user, release, 3);

        assertEquals(3, basketRepository.amountOfItemsInBasket(user));
    }

    @Test
    void given_one_item_when_change_quantity_to_zero_then_error() {
        var user = new UserId("nsu3146");
        var label = new Label("Test-Label", "TST");
        var supplier = new Supplier("Test-Supplier", "Teststraße 44, 3231 Testhausen, Österreich");
        var release = new Release(new ReleaseId(UUID.randomUUID()), 100, "Test-Release", Medium.CD, 23.21, label, List.of(supplier), List.of(new RecordingId(UUID.randomUUID())));
        basketRepository.addItemToBasket(user, release, 2);

        assertThrows(IllegalArgumentException.class, () -> basketRepository.changeQuantityOfItem(user, release, 0));
    }

    @Test
    void given_empty_basket_when_change_quantity_of_item_then_error() {
        var user = new UserId("nsu3146");
        var label = new Label("Test-Label", "TST");
        var supplier = new Supplier("Test-Supplier", "Teststraße 44, 3231 Testhausen, Österreich");
        var release = new Release(new ReleaseId(UUID.randomUUID()), 100, "Test-Release", Medium.CD, 23.21, label, List.of(supplier), List.of(new RecordingId(UUID.randomUUID())));

        assertThrows(IllegalArgumentException.class, () -> basketRepository.changeQuantityOfItem(user, release, 1));
    }

    @Test
    void given_empty_basket_when_add_item_with_zero_quantity_then_error() {
        var user = new UserId("nsu3146");
        var label = new Label("Test-Label", "TST");
        var supplier = new Supplier("Test-Supplier", "Teststraße 44, 3231 Testhausen, Österreich");
        var release = new Release(new ReleaseId(UUID.randomUUID()), 100, "Test-Release", Medium.CD, 23.21, label, List.of(supplier), List.of(new RecordingId(UUID.randomUUID())));
        basketRepository.addItemToBasket(user, release, 2);

        assertThrows(IllegalArgumentException.class, () -> basketRepository.addItemToBasket(user, release, 0));
    }

    @Test
    void given_two_items_in_basket_when_clear_basket_then_basket_is_empty() {
        var user = new UserId("nsu3146");
        var label = new Label("Test-Label", "TST");
        var supplier = new Supplier("Test-Supplier", "Teststraße 44, 3231 Testhausen, Österreich");
        var release1 = new Release(new ReleaseId(UUID.randomUUID()), 100, "Test-Release 1", Medium.CD, 23.21, label, List.of(supplier), List.of(new RecordingId(UUID.randomUUID())));
        var release2 = new Release(new ReleaseId(UUID.randomUUID()), 100, "Test-Release 2", Medium.CD, 23.21, label, List.of(supplier), List.of(new RecordingId(UUID.randomUUID())));
        basketRepository.addItemToBasket(user, release1, 1);
        basketRepository.addItemToBasket(user, release2, 2);

        basketRepository.clearBasket(user);

        var basket = basketRepository.itemsInBasket(user);
        assertTrue(basket.isEmpty());
    }


}
