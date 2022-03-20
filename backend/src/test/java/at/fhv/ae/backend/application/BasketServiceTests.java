package at.fhv.ae.backend.application;

import at.fhv.ae.backend.application.impl.BasketServiceImpl;
import at.fhv.ae.backend.domain.model.release.*;
import at.fhv.ae.backend.domain.model.work.RecordingId;
import at.fhv.ae.backend.domain.repository.BasketRepository;
import at.fhv.ae.backend.domain.repository.ReleaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BasketServiceTests {

    private BasketService basketService;
    private BasketRepository basketRepository;
    private ReleaseRepository releaseRepository;

    @BeforeEach
    void prepareMocksAndTarget() {
        basketRepository = mock(BasketRepository.class); // mock repo
        releaseRepository = mock(ReleaseRepository.class); // mock repo
        basketService = new BasketServiceImpl(basketRepository, releaseRepository); // new instance of application service with mocked repos
    }

    @Test // testing #changeQuantityOfItem
    void given_one_item_in_basket_when_change_quantity_of_item_to_three_then_repo_calls_change_quantity_method() {
        var releaseId = new ReleaseId(UUID.randomUUID());
        var label = new Label("Test-Label", "TST");
        var supplier = new Supplier("Test-Supplier", "Teststraße 44, 3231 Testhausen, Österreich");
        var release = new Release(releaseId, 100, "Test-Release", Medium.CD, 23.21, label, List.of(supplier), List.of(new RecordingId(UUID.randomUUID())));

        when(releaseRepository.findById(releaseId)).thenReturn(Optional.of(release)); // when mock called a certain method then return

        basketService.changeQuantityOfItem(releaseId.id(), 3); // execute method with write call on Repository
        verify(basketRepository).changeQuantityOfItem(release, 3);  // verify if write call was executed at least once
    }

    @Test // testing #removeItemFromBasket
    void given_one_item_in_basket_when_remove_that_item_then_repo_calls_remove() {
        var releaseId = new ReleaseId(UUID.randomUUID());
        var label = new Label("Test-Label", "TST");
        var supplier = new Supplier("Test-Supplier", "Teststraße 44, 3231 Testhausen, Österreich");
        var release = new Release(releaseId, 100, "Test-Release", Medium.CD, 23.21, label, List.of(supplier), List.of(new RecordingId(UUID.randomUUID())));

        when(releaseRepository.findById(releaseId)).thenReturn(Optional.of(release));

        basketService.removeItemFromBasket(releaseId.id());
        verify(basketRepository).removeItemFromBasket(release);
    }

    @Test // testing #addItemToBasket
    void given_item_when_item_added_to_basket_then_repo_calls_add_method() {

        var releaseId = new ReleaseId(UUID.randomUUID());
        var label = new Label("Test-Label", "TST");
        var supplier = new Supplier("Test-Supplier", "Teststraße 44, 3231 Testhausen, Österreich");
        var release = new Release(releaseId, 100, "Test-Release", Medium.CD, 23.21, label, List.of(supplier), List.of(new RecordingId(UUID.randomUUID())));

        when(releaseRepository.findById(releaseId)).thenReturn(Optional.of(release));

        basketService.addItemToBasket(releaseId.id(), 2);
        verify(basketRepository).addItemToBasket(release, 2);
    }


    @Test // testing #itemsInBasket
    void given_empty_basket_when_add_item_to_basket_then_basket_has_size_of_one() {

        var releaseId = new ReleaseId(UUID.randomUUID());
        var label = new Label("Test-Label", "TST");
        var supplier = new Supplier("Test-Supplier", "Teststraße 44, 3231 Testhausen, Österreich");
        var release = new Release(releaseId, 100, "Test-Release", Medium.CD, 23.21, label, List.of(supplier), List.of(new RecordingId(UUID.randomUUID())));
        var returnMap = new HashMap<Release, Integer>();
        returnMap.put(release, 1);

        when(basketRepository.itemsInBasket()).thenReturn(returnMap);

        var releasesAsDTO = basketService.itemsInBasket();

        assertEquals(1, releasesAsDTO.size());
        var dto = releasesAsDTO.get(0);
        assertEquals(release.releaseId().id(), dto.releaseId());
        assertEquals(release.title(), dto.title());
        assertEquals(1, dto.quantity());
        assertEquals(release.medium().friendlyName(), dto.medium());
        assertEquals(release.price(), dto.price());
    }


    @Test // testing #amountOfItemsInBasket
    void given_mock_when_amount_of_items_then_call_amount_of_items_method() {
        basketService.amountOfItemsInBasket();
        verify(basketRepository).amountOfItemsInBasket();
    }




}
