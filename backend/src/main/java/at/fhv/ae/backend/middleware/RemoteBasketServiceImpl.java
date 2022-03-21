package at.fhv.ae.backend.middleware;

import at.fhv.ae.backend.application.BasketService;
import at.fhv.ae.shared.dto.basket.BasketItemRemoteDTO;
import at.fhv.ae.shared.rmi.RemoteBasketService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class RemoteBasketServiceImpl extends UnicastRemoteObject implements RemoteBasketService {

    private final transient BasketService basketService;

    public RemoteBasketServiceImpl(BasketService basketService) throws RemoteException {
        super();
        this.basketService = basketService;
    }


    @Override
    public void addItemToBasket(UUID releaseId, int quantity) {
        basketService.addItemToBasket(releaseId, quantity);
    }

    @Override
    public void changeQuantityOfItem(UUID releaseId, int newQuantity) {
        basketService.changeQuantityOfItem(releaseId, newQuantity);
    }

    @Override
    public void removeItemFromBasket(UUID releaseId) {
        basketService.removeItemFromBasket(releaseId);
    }

    @Override
    public List<BasketItemRemoteDTO> itemsInBasket() {
        return basketService.itemsInBasket()
                .stream()
                .map(dto -> new BasketItemRemoteDTO(dto.releaseId(), dto.title(), dto.quantity(), dto.medium(), dto.price()))
                .collect(Collectors.toList());
    }

    @Override
    public int amountOfItemsInBasket() {
        return basketService.amountOfItemsInBasket();
    }
}
