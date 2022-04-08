package at.fhv.ae.backend.application;

import at.fhv.ae.backend.ServiceRegistry;
import at.fhv.ae.backend.application.impl.SellServiceImpl;
import at.fhv.ae.backend.domain.model.release.*;
import at.fhv.ae.backend.domain.model.user.Permission;
import at.fhv.ae.backend.domain.model.user.Role;
import at.fhv.ae.backend.domain.model.user.User;
import at.fhv.ae.backend.domain.model.user.UserId;
import at.fhv.ae.backend.domain.model.work.RecordingId;
import at.fhv.ae.backend.domain.repository.BasketRepository;
import at.fhv.ae.backend.domain.repository.SaleRepository;
import at.fhv.ae.backend.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.mockito.Mockito.*;


class SellServiceTests {

    private SellService sellService;
    private BasketRepository basketRepository;
    private SaleRepository sellRepository;
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        basketRepository = mock(BasketRepository.class);
        sellRepository = mock(SaleRepository.class);
        userRepository = mock(UserRepository.class);
        sellService = new SellServiceImpl(sellRepository, basketRepository, userRepository, ServiceRegistry.entityManager());
    }

    @Test
    void given_basket_with_items_when_create_sale_then_sale_repo_adds_sale() {
        var userId = new UserId("nsu3146");
        var role = new Role("Seller", Set.of(Permission.SELL_RELEASES, Permission.SEARCH_RELEASES));
        var user = new User(userId, Set.of(role));

        var label = new Label("Test-Label", "TST");
        var supplier = new Supplier("Test-Supplier", "Teststraße 44, 3231 Testhausen, Österreich");
        var release1 = new Release(new ReleaseId(UUID.randomUUID()), 100, "Test-Release 1", Medium.CD, 23.21, label, List.of(supplier), List.of(new RecordingId(UUID.randomUUID())));
        var release2 = new Release(new ReleaseId(UUID.randomUUID()), 100, "Test-Release 2", Medium.CD, 23.21, label, List.of(supplier), List.of(new RecordingId(UUID.randomUUID())));
        var release3 = new Release(new ReleaseId(UUID.randomUUID()), 100, "Test-Release 3", Medium.CD, 23.21, label, List.of(supplier), List.of(new RecordingId(UUID.randomUUID())));
        var basket = Map.of(
                release1, 2,
                release2, 3,
                release3, 1
        );

        when(userRepository.userById(userId)).thenReturn(Optional.of(user));
        when(basketRepository.itemsInBasket(userId)).thenReturn(basket);
        sellService.sellItemsInBasket(userId.name(), null);

        verify(sellRepository).addSale(any());
        verify(basketRepository).clearBasket(userId);

    }

}
