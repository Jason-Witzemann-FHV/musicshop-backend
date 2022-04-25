package at.fhv.ae.backend.infrastructure;

import at.fhv.ae.backend.domain.model.release.Release;
import at.fhv.ae.backend.domain.model.user.UserId;
import at.fhv.ae.backend.domain.repository.BasketRepository;

import javax.ejb.Stateless;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Stateless
public class HashMapBasketRepository implements BasketRepository {

    private static final Map<UserId, Map<Release, Integer>> releases = new HashMap<>();

    @Override
    public Map<Release, Integer> itemsInBasket(UserId userId) {
        return Collections.unmodifiableMap(releases.getOrDefault(userId, Map.of()));
    }

    @Override
    public int amountOfItemsInBasket(UserId userId) {
        return releases.getOrDefault(userId, Map.of()).values().stream().mapToInt(Integer::valueOf).sum();
    }

    @Override
    public void addItemToBasket(UserId userId, Release item, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity of item in Basket must be at least 1!");
        }
        var basket = releases.getOrDefault(userId, new HashMap<>());
        basket.put(item, quantity);
        releases.put(userId, basket);
    }

    @Override
    public void changeQuantityOfItem(UserId userId, Release item, int newQuantity) {
        if (newQuantity <= 0) {
            throw new IllegalArgumentException("Quantity of item in Basket must be at least 1!");
        }
        if (!releases.containsKey(userId)) {
            throw new IllegalArgumentException("Cannot change quantity for a user that has no basket!");
        }
        if (!releases.get(userId).containsKey(item)) {
            throw new IllegalArgumentException("Cannot change quantity of item that is not already in the basket.");
        }
        releases.get(userId).put(item, newQuantity);
    }

    @Override
    public void removeItemFromBasket(UserId userId, Release item) {
        if (!releases.containsKey(userId)) {
            throw new IllegalArgumentException("Cannot change quantity for a user that has no basket!");
        }
        if (!releases.get(userId).containsKey(item)) {
            throw new IllegalArgumentException("Tried to remove item with ID " + item.releaseId() + " from Basket, even though that item is not in basket");
        }
        releases.get(userId).remove(item);
    }

    @Override
    public void clearBasket(UserId userId) {
        releases.remove(userId);
    }
}
