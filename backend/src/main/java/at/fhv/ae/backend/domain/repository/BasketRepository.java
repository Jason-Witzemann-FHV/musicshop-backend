package at.fhv.ae.backend.domain.repository;

import at.fhv.ae.backend.domain.model.release.Release;

import java.util.List;

public interface BasketRepository {

    /**
     *
     * @return an <strong>immutable</strong> list of the current basket
     */
    List<Release> itemsInBasket();

    int amountOfItemsInBasket();

    void addItemToBasket(Release item);

    /**
     * @throws IllegalArgumentException if to be removed Release is not in basket
     */
    void removeItemFromBasket(Release item);

    void clearBasket();

}
