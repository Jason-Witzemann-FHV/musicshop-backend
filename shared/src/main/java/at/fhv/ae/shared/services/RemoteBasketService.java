package at.fhv.ae.shared.services;

import at.fhv.ae.shared.dto.basket.BasketItemRemoteDTO;

import javax.ejb.Remote;
import java.util.List;
import java.util.UUID;

@Remote
public interface RemoteBasketService {

    void init(String userId);

    void addItemToBasket(UUID releaseId, int quantity);

    void changeQuantityOfItem(UUID releaseId, int newQuantity);

    void removeItemFromBasket(UUID releaseId);

    List<BasketItemRemoteDTO> itemsInBasket();

    int amountOfItemsInBasket();

    void clearBasket();
}
