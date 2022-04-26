package at.fhv.ae.backend.middleware.remoteservices;

import at.fhv.ae.backend.application.BasketService;
import at.fhv.ae.shared.dto.basket.BasketItemRemoteDTO;
import at.fhv.ae.shared.services.RemoteBasketService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Stateful
@NoArgsConstructor
@AllArgsConstructor
public class RemoteBasketServiceImpl implements RemoteBasketService {

    @EJB
    private BasketService basketService;

    private String userId;

    @Override
    public void init(String userId) {
        if (this.userId != null) {
            throw new IllegalStateException("Instance already initialized!");
        }
        this.userId = userId;
    }

    @Override
    public void addItemToBasket(UUID releaseId, int quantity) {
        basketService.addItemToBasket(userId, releaseId, quantity);
    }

    @Override
    public void changeQuantityOfItem(UUID releaseId, int newQuantity) {
        basketService.changeQuantityOfItem(userId, releaseId, newQuantity);
    }

    @Override
    public void removeItemFromBasket(UUID releaseId) {
        basketService.removeItemFromBasket(userId, releaseId);
    }

    @Override
    public List<BasketItemRemoteDTO> itemsInBasket() {
        return basketService.itemsInBasket(userId)
                .stream()
                .map(dto -> new BasketItemRemoteDTO(dto.releaseId(), dto.title(), dto.quantity(), dto.stock(), dto.medium(), dto.price()))
                .collect(Collectors.toList());
    }

    @Override
    public int amountOfItemsInBasket() {
        return basketService.amountOfItemsInBasket(userId);
    }

    @Override
    public void clearBasket() {
        basketService.clearBasket(userId);
    }
}
