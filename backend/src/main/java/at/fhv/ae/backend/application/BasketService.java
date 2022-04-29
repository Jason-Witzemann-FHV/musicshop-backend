package at.fhv.ae.backend.application;


import at.fhv.ae.backend.application.dto.BasketItemDisplayDTO;

import javax.ejb.Local;
import java.util.List;
import java.util.UUID;

@Local
public interface BasketService {

    void addItemToBasket(String userId, UUID releaseId, int quantity);

    void changeQuantityOfItem(String userId, UUID releaseId, int newQuantity);

    void removeItemFromBasket(String userId, UUID releaseId);

    List<BasketItemDisplayDTO> itemsInBasket(String userId);

    int amountOfItemsInBasket(String userId);

    void clearBasket(String userId);


}
