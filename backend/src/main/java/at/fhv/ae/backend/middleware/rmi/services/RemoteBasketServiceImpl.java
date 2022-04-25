package at.fhv.ae.backend.middleware.rmi.services;

import at.fhv.ae.backend.application.BasketService;
import at.fhv.ae.shared.dto.basket.BasketItemRemoteDTO;
import at.fhv.ae.shared.rmi.RemoteBasketService;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.inject.Inject;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Stateful
public class RemoteBasketServiceImpl implements RemoteBasketService {

    @Resource
    private SessionContext context;

    @EJB
    private BasketService basketService;

    private String userId;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public RemoteBasketServiceImpl() {

    }

    public RemoteBasketServiceImpl(String userId, BasketService basketService)  {
        super();
        this.basketService = basketService;
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
