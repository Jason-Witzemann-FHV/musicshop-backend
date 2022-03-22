package at.fhv.ae.backend.application.impl;

import at.fhv.ae.backend.application.SellService;
import at.fhv.ae.backend.domain.model.release.Release;
import at.fhv.ae.backend.domain.model.sale.*;
import at.fhv.ae.backend.domain.repository.BasketRepository;
import at.fhv.ae.backend.domain.repository.SaleRepository;
import lombok.AllArgsConstructor;

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
    private final EntityManager entityManager;

    @Override
    public boolean sellItemsInBasket() {
        try {
            Map<Release, Integer> basket = basketRepository.itemsInBasket();

            List<Item> saleItems = basket.entrySet()
                    .stream()
                    .map(entry -> new Item(entry.getKey().releaseId(), entry.getValue(), entry.getKey().price()))
                    .collect(Collectors.toList());

            Sale sale = Sale.create(new SaleId(UUID.randomUUID()),
                    "anonymous Employee",
                    "anonymous Customer",
                    PaymentType.CASH, // First sprint only supports cash sale
                    SaleType.IN_PERSON,
                    saleItems
            );

            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            saleRepository.addSale(sale);
            transaction.commit();
            basketRepository.clearBasket();

        } catch (Exception e) { // only case of unsuccessful persist of sale is an unexpected exception
            return false;
        }
        return true;
    }

}
