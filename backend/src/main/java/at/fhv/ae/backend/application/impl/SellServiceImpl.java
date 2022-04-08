package at.fhv.ae.backend.application.impl;

import at.fhv.ae.backend.application.SellService;
import at.fhv.ae.backend.domain.model.release.Release;
import at.fhv.ae.backend.domain.model.release.ReleaseId;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
public class SellServiceImpl implements SellService {

    private final SaleRepository saleRepository;
    private final BasketRepository basketRepository;
    private final UserRepository userRepository;
    private final ReleaseRepository releaseRepository;
    private final EntityManager entityManager;


    @Override
    public boolean sellItemsInBasket(String userId, ObjectId customerId) {
        var user = userRepository.userById(new UserId(userId)).orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " was not found!"));

        try {
            Map<Release, Integer> basket = basketRepository.itemsInBasket(user.userId());

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
            transaction.commit();
            basketRepository.clearBasket(user.userId());

        } catch (Exception e) { // only case of unsuccessful persist of sale is an unexpected exception
            return false;
        }
        return true;
    }

    @Override
    public void sellBasket(HashMap<ReleaseId, Integer> saleItems)  {
        boolean inStock = true;

        for (Map.Entry<ReleaseId, Integer> entry: saleItems.entrySet()){
            ReleaseId releaseId = entry.getKey();
            Integer amount = entry.getValue();

            var stock = releaseRepository.currentStock(releaseId);

            if (amount > stock){
                inStock = false;
            } else {
                try {
                    throw new Exception();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if (inStock) {
            for (Map.Entry<ReleaseId, Integer> entry : saleItems.entrySet()) {
                ReleaseId releaseId = entry.getKey();
                Integer amount = entry.getValue();

                EntityTransaction transaction = entityManager.getTransaction();
                transaction.begin();
                releaseRepository.decreaseStock(releaseId, amount);
                transaction.commit();

            }
        }
    }
}
