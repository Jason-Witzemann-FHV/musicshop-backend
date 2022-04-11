package at.fhv.ae.backend.application.impl;

import at.fhv.ae.backend.application.exceptions.OutOfStockException;
import at.fhv.ae.backend.application.SellService;
import at.fhv.ae.backend.domain.model.release.Release;
import at.fhv.ae.backend.domain.model.sale.*;
import at.fhv.ae.backend.domain.model.user.UserId;
import at.fhv.ae.backend.domain.repository.BasketRepository;
import at.fhv.ae.backend.domain.repository.ReleaseRepository;
import at.fhv.ae.backend.domain.repository.SaleRepository;
import at.fhv.ae.backend.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
public class SellServiceImpl implements SellService {

    private final SaleRepository saleRepository;
    private final BasketRepository basketRepository;
    private final UserRepository userRepository;
    private final EntityManager entityManager;

    @Override
    public void sellItemsInBasket(String userId, ObjectId customerId) throws OutOfStockException {

        var user = userRepository.userById(new UserId(userId))
                .orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " was not found!"));

        Map<Release, Integer> basket = basketRepository.itemsInBasket(user.userId());

        for (var item : basket.entrySet()) {
            int amount = item.getValue();
            int stock = item.getKey().stock();

            if (amount > stock) {
                throw new OutOfStockException();
            }
        }

        List<Item> saleItems = basket.entrySet()
                .stream()
                .map(entry -> new Item(entry.getKey().releaseId(), entry.getValue(), entry.getKey().price()))
                .collect(Collectors.toList());

        Sale sale = Sale.create(new SaleId(UUID.randomUUID()),
                user.userId(),
                customerId,
                PaymentType.CASH, // First sprint only supports cash sale
                SaleType.IN_PERSON,
                saleItems
        );

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        saleRepository.addSale(sale);

        for (var item : basket.entrySet()) {
            int amount = item.getValue();
            item.getKey().decreaseStock(amount);
        }

        basketRepository.clearBasket(user.userId());

        transaction.commit();
    }
}