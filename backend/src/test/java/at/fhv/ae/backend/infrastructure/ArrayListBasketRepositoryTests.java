package at.fhv.ae.backend.infrastructure;

import at.fhv.ae.backend.ServiceRegistry;
import at.fhv.ae.backend.domain.model.release.*;
import at.fhv.ae.backend.domain.model.work.RecordingId;
import at.fhv.ae.backend.domain.repository.BasketRepository;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


class ArrayListBasketRepositoryTests {

    private BasketRepository basketRepository = ServiceRegistry.basketRepository();

    @BeforeEach
    void cleanBefore() {
        basketRepository.clearBasket();
        Assumptions.assumeTrue(basketRepository.itemsInBasket().isEmpty(), "Basket Repo failed to clear itself, therefore test was skipped.");
    }

    @Test
    void given_no_items_in_basket_when_get_basket_items_in_basket_then_empty_list() {
        var basket = basketRepository.itemsInBasket();
        assertNotNull(basket);
        assertTrue(basket.isEmpty());
    }

    @Test
    void given_some_items_in_basket_when_get_basket_items_in_basket_then_empty_list() {
        var label = new Label("Test-Label", "TST");
        var supplier = new Supplier("Test-Supplier", "Teststraße 44, 3231 Testhausen, Österreich");
        var release = new Release(new ReleaseId(UUID.randomUUID()), 100, "Test-Release", Medium.CD, 23.21, label, List.of(supplier), List.of(new RecordingId(UUID.randomUUID())));
        basketRepository.addItemToBasket(release);

        var basket = basketRepository.itemsInBasket();
        assertNotNull(basket);
        assertEquals(1, basket.size());

    }

    @Test
    void given_three_items_in_basket_when_get_amount_of_items_then_return_three() {
        var label = new Label("Test-Label", "TST");
        var supplier = new Supplier("Test-Supplier", "Teststraße 44, 3231 Testhausen, Österreich");
        var release1 = new Release(new ReleaseId(UUID.randomUUID()), 100, "Test-Release 1", Medium.CD, 23.21, label, List.of(supplier), List.of(new RecordingId(UUID.randomUUID())));
        var release2 = new Release(new ReleaseId(UUID.randomUUID()), 100, "Test-Release 2", Medium.CD, 23.21, label, List.of(supplier), List.of(new RecordingId(UUID.randomUUID())));
        var release3 = new Release(new ReleaseId(UUID.randomUUID()), 100, "Test-Release 3", Medium.CD, 23.21, label, List.of(supplier), List.of(new RecordingId(UUID.randomUUID())));
        basketRepository.addItemToBasket(release1);
        basketRepository.addItemToBasket(release2);
        basketRepository.addItemToBasket(release3);

        var amountOfItemsInBasket = basketRepository.amountOfItemsInBasket();
        assertEquals(3, amountOfItemsInBasket);
    }


    @Test
    void given_two_items_in_basket_when_remove_one_item_then_other_item_is_only_one_in_basket() {
        var label = new Label("Test-Label", "TST");
        var supplier = new Supplier("Test-Supplier", "Teststraße 44, 3231 Testhausen, Österreich");
        var release1 = new Release(new ReleaseId(UUID.randomUUID()), 100, "Test-Release 1", Medium.CD, 23.21, label, List.of(supplier), List.of(new RecordingId(UUID.randomUUID())));
        var release2 = new Release(new ReleaseId(UUID.randomUUID()), 100, "Test-Release 2", Medium.CD, 23.21, label, List.of(supplier), List.of(new RecordingId(UUID.randomUUID())));
        basketRepository.addItemToBasket(release1);
        basketRepository.addItemToBasket(release2);

        basketRepository.removeItemFromBasket(release1);

        var basket = basketRepository.itemsInBasket();
        assertEquals(1, basket.size());
        assertEquals(release2, basket.get(0));
        assertNotEquals(release1, basket.get(0));
    }

    @Test
    void given_basket_when_try_to_add_item_to_basket_object_then_error() {

        var basket = basketRepository.itemsInBasket();

        var label = new Label("Test-Label", "TST");
        var supplier = new Supplier("Test-Supplier", "Teststraße 44, 3231 Testhausen, Österreich");
        var release = new Release(new ReleaseId(UUID.randomUUID()), 100, "Test-Release 1", Medium.CD, 23.21, label, List.of(supplier), List.of(new RecordingId(UUID.randomUUID())));

        assertThrows(Exception.class, () -> basket.add(release));
    }

    @Test
    void given_one_item_in_basket_when_try_remove_other_item_then_throw_illegal_state_exception() {

        var label = new Label("Test-Label", "TST");
        var supplier = new Supplier("Test-Supplier", "Teststraße 44, 3231 Testhausen, Österreich");
        var releaseInBasket = new Release(new ReleaseId(UUID.randomUUID()), 100, "Test-Release 1", Medium.CD, 23.21, label, List.of(supplier), List.of(new RecordingId(UUID.randomUUID())));
        basketRepository.addItemToBasket(releaseInBasket);

        var releaseNotInBasket = new Release(new ReleaseId(UUID.randomUUID()), 100, "Test-Release 2", Medium.CD, 23.21, label, List.of(supplier), List.of(new RecordingId(UUID.randomUUID())));

        assertThrows(IllegalArgumentException.class, () -> basketRepository.removeItemFromBasket(releaseNotInBasket));

    }

    @Test
    void given_two_items_in_basket_when_clear_basket_then_basket_is_empty() {
        var label = new Label("Test-Label", "TST");
        var supplier = new Supplier("Test-Supplier", "Teststraße 44, 3231 Testhausen, Österreich");
        var release1 = new Release(new ReleaseId(UUID.randomUUID()), 100, "Test-Release 1", Medium.CD, 23.21, label, List.of(supplier), List.of(new RecordingId(UUID.randomUUID())));
        var release2 = new Release(new ReleaseId(UUID.randomUUID()), 100, "Test-Release 2", Medium.CD, 23.21, label, List.of(supplier), List.of(new RecordingId(UUID.randomUUID())));
        basketRepository.addItemToBasket(release1);
        basketRepository.addItemToBasket(release2);

        basketRepository.clearBasket();

        var basket = basketRepository.itemsInBasket();
        assertTrue(basket.isEmpty());
    }


}
