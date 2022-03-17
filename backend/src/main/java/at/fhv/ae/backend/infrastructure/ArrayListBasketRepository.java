package at.fhv.ae.backend.infrastructure;

import at.fhv.ae.backend.domain.model.release.Release;
import at.fhv.ae.backend.domain.repository.BasketRepository;

import java.util.ArrayList;
import java.util.List;

public class ArrayListBasketRepository implements BasketRepository {

    private static List<Release> releases = new ArrayList<>();

    @Override
    public List<Release> itemsInBasket() {
        return List.copyOf(releases);
    }

    @Override
    public int amountOfItemsInBasket() {
        return releases.size();
    }

    @Override
    public void addItemToBasket(Release item) {
        releases.add(item);
    }

    @Override
    public void removeItemFromBasket(Release item) {
        if (!releases.contains(item)) {
            throw new IllegalArgumentException("Tried to remove item with ID " + item.releaseId() + " from Basket, even though that item is not in basket");
        }
        releases.remove(item);
    }

    @Override
    public void clearBasket() {
        releases.clear();
    }
}
