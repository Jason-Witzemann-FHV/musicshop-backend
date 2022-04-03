package at.fhv.ae.backend.domain.repository;

import at.fhv.ae.backend.domain.model.release.Release;
import at.fhv.ae.backend.domain.model.user.UserId;

import java.util.Map;

public interface BasketRepository {

    /**
     * @return an <strong>immutable</strong> map of the current basket
     */
    Map<Release, Integer> itemsInBasket(UserId userId);

    int amountOfItemsInBasket(UserId userId);

    /**
     * if an item is already in basket, the quantity gets overridden
     * @throws IllegalArgumentException if newQuantity is <= 0
     */
    void addItemToBasket(UserId userId, Release item, int quantity);

    /**
     * @throws IllegalArgumentException if release is not in basket
     * @throws IllegalArgumentException if newQuantity is <= 0
     */
    void changeQuantityOfItem(UserId userid, Release item, int newQuantity);

    /**
     * @throws IllegalArgumentException if to be removed Release is not in basket
     */
    void removeItemFromBasket(UserId userId, Release item);

    void clearBasket(UserId userId);

}
