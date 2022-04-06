package at.fhv.ae.backend.application;

import at.fhv.ae.backend.ServiceRegistry;
import at.fhv.ae.backend.application.impl.BasketServiceImpl;
import at.fhv.ae.backend.domain.model.release.*;
import at.fhv.ae.backend.domain.model.user.Permission;
import at.fhv.ae.backend.domain.model.user.Role;
import at.fhv.ae.backend.domain.model.user.User;
import at.fhv.ae.backend.domain.model.user.UserId;
import at.fhv.ae.backend.domain.model.work.RecordingId;
import at.fhv.ae.backend.domain.repository.BasketRepository;
import at.fhv.ae.backend.domain.repository.ReleaseRepository;
import at.fhv.ae.backend.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BasketServiceTests {

    private BasketService basketService;
    private BasketRepository basketRepository;
    private ReleaseRepository releaseRepository;
    private UserRepository userRepository;

    @BeforeEach
    void prepareMocksAndTarget() {
        basketRepository = mock(BasketRepository.class); // mock repo
        releaseRepository = mock(ReleaseRepository.class); // mock repo
        userRepository = mock(UserRepository.class); // mock repo
        basketService = new BasketServiceImpl(basketRepository, releaseRepository, userRepository); // new instance of application service with mocked repos
    }

    @Test // testing #changeQuantityOfItem
    void given_one_item_in_basket_when_change_quantity_of_item_to_three_then_repo_calls_change_quantity_method() {
        var userId = new UserId("nsu3146");
        var role = new Role("Seller", Set.of(Permission.SELL_RELEASES, Permission.SEARCH_RELEASES));
        var user = new User(userId, Set.of(role));

        var releaseId = new ReleaseId(UUID.randomUUID());
        var label = new Label("Test-Label", "TST");
        var supplier = new Supplier("Test-Supplier", "Teststraße 44, 3231 Testhausen, Österreich");
        var release = new Release(releaseId, 100, "Test-Release", Medium.CD, 23.21, label, List.of(supplier), List.of(new RecordingId(UUID.randomUUID())));

        when(releaseRepository.findById(releaseId)).thenReturn(Optional.of(release)); // when mock called a certain method then return
        when(userRepository.userById(userId)).thenReturn(Optional.of(user));

        basketService.changeQuantityOfItem(userId.name(), releaseId.id(), 3); // execute method with write call on Repository
        verify(basketRepository).changeQuantityOfItem(userId, release, 3);  // verify if write call was executed at least once
    }

    @Test // testing #removeItemFromBasket
    void given_one_item_in_basket_when_remove_that_item_then_repo_calls_remove() {
        var userId = new UserId("nsu3146");
        var role = new Role("Seller", Set.of(Permission.SELL_RELEASES, Permission.SEARCH_RELEASES));
        var user = new User(userId, Set.of(role));

        var releaseId = new ReleaseId(UUID.randomUUID());
        var label = new Label("Test-Label", "TST");
        var supplier = new Supplier("Test-Supplier", "Teststraße 44, 3231 Testhausen, Österreich");
        var release = new Release(releaseId, 100, "Test-Release", Medium.CD, 23.21, label, List.of(supplier), List.of(new RecordingId(UUID.randomUUID())));

        when(releaseRepository.findById(releaseId)).thenReturn(Optional.of(release));
        when(userRepository.userById(userId)).thenReturn(Optional.of(user));

        basketService.removeItemFromBasket(userId.name(), releaseId.id());
        verify(basketRepository).removeItemFromBasket(userId, release);
    }

    @Test // testing #addItemToBasket
    void given_item_when_item_added_to_basket_then_repo_calls_add_method() {
        var userId = new UserId("nsu3146");
        var role = new Role("Seller", Set.of(Permission.SELL_RELEASES, Permission.SEARCH_RELEASES));
        var user = new User(userId, Set.of(role));

        var releaseId = new ReleaseId(UUID.randomUUID());
        var label = new Label("Test-Label", "TST");
        var supplier = new Supplier("Test-Supplier", "Teststraße 44, 3231 Testhausen, Österreich");
        var release = new Release(releaseId, 100, "Test-Release", Medium.CD, 23.21, label, List.of(supplier), List.of(new RecordingId(UUID.randomUUID())));

        when(releaseRepository.findById(releaseId)).thenReturn(Optional.of(release));
        when(userRepository.userById(userId)).thenReturn(Optional.of(user));


        basketService.addItemToBasket(userId.name(), releaseId.id(), 2);
        verify(basketRepository).addItemToBasket(userId, release, 2);
    }


    @Test // testing #itemsInBasket
    void given_empty_basket_when_add_item_to_basket_then_basket_has_size_of_one() {
        var userId = new UserId("nsu3146");
        var role = new Role("Seller", Set.of(Permission.SELL_RELEASES, Permission.SEARCH_RELEASES));
        var user = new User(userId, Set.of(role));

        var releaseId = new ReleaseId(UUID.randomUUID());
        var label = new Label("Test-Label", "TST");
        var supplier = new Supplier("Test-Supplier", "Teststraße 44, 3231 Testhausen, Österreich");
        var release = new Release(releaseId, 100, "Test-Release", Medium.CD, 23.21, label, List.of(supplier), List.of(new RecordingId(UUID.randomUUID())));
        var returnMap = new HashMap<Release, Integer>();
        returnMap.put(release, 1);

        when(userRepository.userById(userId)).thenReturn(Optional.of(user));
        when(basketRepository.itemsInBasket(userId)).thenReturn(returnMap);


        var releasesAsDTO = basketService.itemsInBasket(userId.name());

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
        var userId = new UserId("nsu3146");
        var role = new Role("Seller", Set.of(Permission.SELL_RELEASES, Permission.SEARCH_RELEASES));
        var user = new User(userId, Set.of(role));

        when(userRepository.userById(userId)).thenReturn(Optional.of(user));

        basketService.amountOfItemsInBasket(userId.name());
        verify(basketRepository).amountOfItemsInBasket(userId);
    }




}
