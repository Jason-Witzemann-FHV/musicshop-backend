package at.fhv.ae.backend.domain.repository;

import at.fhv.ae.backend.domain.model.release.Release;

import java.util.Map;

public interface BasketRepository {

    /**
     * @return an <strong>immutable</strong> map of the current basket
     */
    Map<Release, Integer> itemsInBasket();

    int amountOfItemsInBasket();

    /**
     * if an item is already in basket, the quantity gets overridden
     * @throws IllegalArgumentException if newQuantity is <= 0
     */
    void addItemToBasket(Release item, int quantity);

    /**
     * @throws IllegalArgumentException if release is not in basket
     * @throws IllegalArgumentException if newQuantity is <= 0
     */
    void changeQuantityOfItem(Release item, int newQuantity);

    /**
     * @throws IllegalArgumentException if to be removed Release is not in basket
     */
    void removeItemFromBasket(Release item);

    void clearBasket();

}
