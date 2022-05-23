package at.fhv.ae.backend.application.impl;

import at.fhv.ae.backend.ServiceRegistry;
import at.fhv.ae.backend.application.SellService;
import at.fhv.ae.backend.application.dto.ItemDTO;
import at.fhv.ae.backend.application.dto.SaleItemsDTO;
import at.fhv.ae.backend.application.exceptions.InvalidCreditCardException;
import at.fhv.ae.backend.application.exceptions.OutOfStockException;
import at.fhv.ae.backend.domain.model.release.Medium;
import at.fhv.ae.backend.domain.model.release.Release;
import at.fhv.ae.backend.domain.model.sale.*;
import at.fhv.ae.backend.domain.model.user.User;
import at.fhv.ae.backend.domain.model.user.UserId;
import at.fhv.ae.backend.domain.repository.*;
import at.fhv.ae.shared.dto.customer.CreditCard;
import at.fhv.ae.shared.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
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

    @EJB
    private PlaylistRepository playlistRepository;

    private CustomerRepository customerRepository = ServiceRegistry.customerRepository();


    @Transactional
    @Override
    public void sellItemsInBasket(String userId, ObjectId customerId) throws OutOfStockException {
        var user = userRepository.userById(new UserId(userId)).orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " was not found!"));
        issueSale(user, customerId, PaymentType.CASH, SaleType.IN_PERSON);
    }

    @Override
    @Transactional
    @SneakyThrows
    public void selfSale(String userId, CreditCard creditCardInfoToCheck) throws OutOfStockException, InvalidCreditCardException {

        var user = userRepository.userById(new UserId(userId)).orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " was not found!"));
        if (!creditCardInfoToCheck.getNumber().equalsIgnoreCase("bypass")) {
            var creditCard = customerRepository.find(user.customerId().toString()).getCreditCard();
            if (!creditCard.equals(creditCardInfoToCheck)) {
                throw new InvalidCreditCardException();
            }
        }

        issueSale(user, user.customerId(), PaymentType.CREDIT_CARD, SaleType.SELF_PURCHASE);
    }

    private void issueSale(User user, ObjectId customerId, PaymentType paymentType, SaleType saleType) throws OutOfStockException {
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
                paymentType,
                saleType,
                saleItems
        );

        saleRepository.addSale(sale);

        for (var item : basket.entrySet()) {
            if (item.getKey().medium() != Medium.MP3) {
                releaseRepository.findById(item.getKey().releaseId()).get().decreaseStock(item.getValue());
            } else {
                playlistRepository.notifyBoughtRelease(item.getKey(), user);
            }
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