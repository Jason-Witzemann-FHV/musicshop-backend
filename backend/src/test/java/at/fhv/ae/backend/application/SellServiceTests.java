package at.fhv.ae.backend.application;

import at.fhv.ae.backend.ServiceRegistry;
import at.fhv.ae.backend.application.dto.ItemDTO;
import at.fhv.ae.backend.application.dto.SaleItemsDTO;
import at.fhv.ae.backend.application.impl.SellServiceImpl;
import at.fhv.ae.backend.domain.model.release.*;
import at.fhv.ae.backend.domain.model.sale.*;
import at.fhv.ae.backend.domain.model.user.Permission;
import at.fhv.ae.backend.domain.model.user.Role;
import at.fhv.ae.backend.domain.model.user.User;
import at.fhv.ae.backend.domain.model.user.UserId;
import at.fhv.ae.backend.domain.model.work.RecordingId;
import at.fhv.ae.backend.domain.repository.BasketRepository;
import at.fhv.ae.backend.domain.repository.ReleaseRepository;
import at.fhv.ae.backend.domain.repository.SaleRepository;
import at.fhv.ae.backend.domain.repository.UserRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class SellServiceTests {

    private SellService sellService;
    private BasketRepository basketRepository;
    private SaleRepository sellRepository;
    private UserRepository userRepository;
    private ReleaseRepository releaseRepository;

    @BeforeEach
    void setup() {
        basketRepository = mock(BasketRepository.class);
        sellRepository = mock(SaleRepository.class);
        userRepository = mock(UserRepository.class);
        releaseRepository = mock(ReleaseRepository.class);
        sellService = new SellServiceImpl(sellRepository, basketRepository, releaseRepository, userRepository, ServiceRegistry.entityManager());
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
        assertDoesNotThrow(() -> sellService.sellItemsInBasket(userId.name(), null));

        verify(sellRepository).addSale(any());
        verify(basketRepository).clearBasket(userId);

    }

    @Test
    void given_sales_on_employee_when_get_sales_of_employee_then_return_all_sales() {

        // prepare release
        var label = new Label("Test-Label", "TST");
        var supplier = new Supplier("Test-Supplier", "Teststraße 44, 3231 Testhausen, Österreich");
        var releases = List.of(
                new Release(new ReleaseId(UUID.randomUUID()), 100, "Test-Release 1", Medium.CD, 9.99, label, List.of(supplier), List.of(new RecordingId(UUID.randomUUID()))),
                new Release(new ReleaseId(UUID.randomUUID()), 100, "Test-Release 2", Medium.CD, 19.99, label, List.of(supplier), List.of(new RecordingId(UUID.randomUUID()))),
                new Release(new ReleaseId(UUID.randomUUID()), 100, "Test-Release 3", Medium.CD, 29.99, label, List.of(supplier), List.of(new RecordingId(UUID.randomUUID())))
        );

        // prepare sale
        var userId = new UserId("nsu3146");
        var saleItems = releases.stream()
                .map(release -> new Item(release.releaseId(), 3, release.price()))
                .collect(Collectors.toList());
        var sale = Sale.create(new SaleId(UUID.randomUUID()),
                userId,
                ObjectId.get(),
                PaymentType.CASH, // First sprint only supports cash sale
                SaleType.IN_PERSON,
                saleItems
        );

        // prepare expected result dto
        var sellItemDTOs = releases.stream()
                .map(release -> new ItemDTO(release.title(), 3, release.price()))
                .collect(Collectors.toList());
        var saleItemsDTO = new SaleItemsDTO( // expected value
                sale.saleId().toString(),
                sale.sellTimestamp().toString(),
                sale.customerId().toString(),
                sale.totalPrice(),
                sellItemDTOs
        );


        when(sellRepository.salesOfUser(userId)).thenReturn(List.of(sale));
        for (int i = 0; i < saleItems.size(); i++) {
            when(releaseRepository.findById(saleItems.get(i).releaseId())).thenReturn(Optional.of(releases.get(i)));
        }

        var actual = sellService.salesOfUser(userId.toString());

        assertEquals(List.of(saleItemsDTO), actual);


    }



}
