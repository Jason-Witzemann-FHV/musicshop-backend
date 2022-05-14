package at.fhv.ae.backend.application.impl;

import at.fhv.ae.backend.application.SellService;
import at.fhv.ae.backend.application.dto.ItemDTO;
import at.fhv.ae.backend.application.dto.SaleItemsDTO;
import at.fhv.ae.backend.application.exceptions.OutOfStockException;
import at.fhv.ae.backend.domain.model.release.Release;
import at.fhv.ae.backend.domain.model.sale.*;
import at.fhv.ae.backend.domain.model.user.UserId;
import at.fhv.ae.backend.domain.repository.BasketRepository;
import at.fhv.ae.backend.domain.repository.ReleaseRepository;
import at.fhv.ae.backend.domain.repository.SaleRepository;
import at.fhv.ae.backend.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Stateless
@NoArgsConstructor
@AllArgsConstructor
public class SellServiceImpl implements SellService {

    @EJB
    private SaleRepository saleRepository;

    @EJB
    private BasketRepository basketRepository;

    @EJB
    private ReleaseRepository releaseRepository;

    @EJB
    private UserRepository userRepository;

    @Transactional()
    @Override
    public void sellItemsInBasket(String userId, ObjectId customerId) throws OutOfStockException {
        sell(userId, customerId, SaleType.IN_PERSON);
    }

    @Override
    public void selfSale(String userId) throws OutOfStockException  {
        sell(userId, null, SaleType.SELF_PURCHASE);
    }

    private void sell(String userId, ObjectId customerId, SaleType saleType) throws OutOfStockException {
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

        SaleId saleId = new SaleId(saleRepository.next_sequenceNumber());
        Sale sale = Sale.create(
                saleId,
                user.userId(),
                customerId,
                PaymentType.CASH, // First sprint only supports cash sale
                saleType,
                saleItems
        );

        saleRepository.addSale(sale);

        for (var item : basket.entrySet()) {
            int amount = item.getValue();
            item.getKey().decreaseStock(amount);
        }

        basketRepository.clearBasket(user.userId());
    }

    @Transactional
    @Override
    public List<SaleItemsDTO> allSales() {
        return saleRepository.allSales().stream()
                .map(sale -> new SaleItemsDTO(
                        sale.saleId().id(),
                        sale.sellTimestamp().toString(),
                        Optional.ofNullable(sale.customerId()).map(ObjectId::toString).orElse("anonymous"),
                        sale.totalPrice(),
                        sale.items().stream().map(item -> {
                            var release = releaseRepository.findById(item.releaseId()).get();
                            return ItemDTO.fromDomain(item, release);
                        }).collect(Collectors.toList())
                )).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Optional<SaleItemsDTO> searchSale(int saleNum) {
        return saleRepository.findById(new SaleId(saleNum)).map(sale -> {
                var items = sale.items()
                        .stream()
                        .map(item -> {
                            var release = releaseRepository.findById(item.releaseId()).get();
                            return ItemDTO.fromDomain(item, release);
                        }).collect(Collectors.toList());
                return SaleItemsDTO.fromDomain(sale, items);
        });
    }
}