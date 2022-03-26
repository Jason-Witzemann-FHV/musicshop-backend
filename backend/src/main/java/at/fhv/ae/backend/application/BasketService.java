package at.fhv.ae.backend.application;


import at.fhv.ae.backend.application.dto.BasketItemDisplayDTO;

import java.util.List;
import java.util.UUID;

public interface BasketService {

    void addItemToBasket(UUID releaseId, int quantity);

    void changeQuantityOfItem(UUID releaseId, int newQuantity);

    void removeItemFromBasket(UUID releaseId);

    List<BasketItemDisplayDTO> itemsInBasket();

    int amountOfItemsInBasket();

    void clearBasket();


}
