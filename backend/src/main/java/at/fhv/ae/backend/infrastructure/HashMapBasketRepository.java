package at.fhv.ae.backend.infrastructure;

import at.fhv.ae.backend.domain.model.release.Release;
import at.fhv.ae.backend.domain.repository.BasketRepository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HashMapBasketRepository implements BasketRepository {

    private static final Map<Release, Integer> releases = new HashMap<>();

    @Override
    public Map<Release, Integer> itemsInBasket() {
        return Collections.unmodifiableMap(releases);
    }

    @Override
    public int amountOfItemsInBasket() {
        return releases.values().stream().mapToInt(Integer::valueOf).sum();
    }

    @Override
    public void addItemToBasket(Release item) {
        releases.add(item);
    }

    @Override
    public void removeItemFromBasket(Release item) {
        if (!releases.containsKey(item)) {
            throw new IllegalArgumentException("Tried to remove item with ID " + item.releaseId() + " from Basket, even though that item is not in basket");
        }
        releases.remove(item);
    }

    @Override
    public void clearBasket() {
        releases.clear();
    }
}
