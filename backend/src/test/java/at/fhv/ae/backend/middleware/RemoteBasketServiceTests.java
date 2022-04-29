package at.fhv.ae.backend.middleware;


import at.fhv.ae.backend.application.BasketService;
import at.fhv.ae.backend.application.dto.BasketItemDisplayDTO;
import at.fhv.ae.backend.middleware.remoteservices.RemoteBasketServiceImpl;
import at.fhv.ae.shared.services.RemoteBasketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class RemoteBasketServiceTests {

    private String userId;
    private BasketService basketService;
    private RemoteBasketService remoteBasketService;

    @BeforeEach
    void setupMocksAndTestClass() throws RemoteException {
        basketService = mock(BasketService.class);
        userId = "nsu3146";
        remoteBasketService = new RemoteBasketServiceImpl(basketService, userId);

    }

    @Test
    void given_setup_when_add_to_basket_then_application_method_called() throws RemoteException {
        var id = UUID.randomUUID();
        var quantity = 2;
        remoteBasketService.addItemToBasket(id, quantity);

        verify(basketService).addItemToBasket("nsu3146", id, quantity);
    }

    @Test
    void given_setup_when_change_basket_then_application_method_called() throws RemoteException {
        var id = UUID.randomUUID();
        var newQuantity = 2;
        remoteBasketService.changeQuantityOfItem(id, newQuantity);

        verify(basketService).changeQuantityOfItem(userId, id, newQuantity);
    }

    @Test
    void given_setup_when_remove_from_basket_then_application_method_called() throws RemoteException {
        var id = UUID.randomUUID();
        remoteBasketService.removeItemFromBasket(id);

        verify(basketService).removeItemFromBasket(userId, id);
    }

    @Test
    void given_basket_with_content_when_get_all_items_then_application_method_called_and_list_of_dto() throws RemoteException {

        var items = List.of(
                new BasketItemDisplayDTO(UUID.randomUUID(), "TestRelease 1", 2, 5,"Vinyl", 32.33),
                new BasketItemDisplayDTO(UUID.randomUUID(), "TestRelease 2", 4, 6, "CD", 4.30),
                new BasketItemDisplayDTO(UUID.randomUUID(), "TestRelease 2", 2, 10,"Music cassette", 8.22)
        );
        when(basketService.itemsInBasket(userId)).thenReturn(items);
        var remoteItems = remoteBasketService.itemsInBasket();
        assertEquals(items.size(), remoteItems.size());
        for (int i = 0; i < items.size(); i++) {
            assertEquals(items.get(i).releaseId(), remoteItems.get(i).getReleaseId());
            assertEquals(items.get(i).title(), remoteItems.get(i).getTitle());
            assertEquals(items.get(i).quantity(), remoteItems.get(i).getQuantity());
            assertEquals(items.get(i).medium(), remoteItems.get(i).getMedium());
            assertEquals(items.get(i).price(), remoteItems.get(i).getPrice());
        }

        verify(basketService).itemsInBasket(userId);
    }

    @Test
    void given_setup_when_get_amount_of_items_from_basket_then_application_method_called() throws RemoteException {
        remoteBasketService.amountOfItemsInBasket();
        verify(basketService).amountOfItemsInBasket(userId);
    }

}
