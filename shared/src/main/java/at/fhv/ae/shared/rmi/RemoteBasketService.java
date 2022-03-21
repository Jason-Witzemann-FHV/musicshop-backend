package at.fhv.ae.shared.rmi;

import at.fhv.ae.shared.dto.basket.BasketItemRemoteDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.UUID;

public interface RemoteBasketService extends Remote {

    void addItemToBasket(UUID releaseId, int quantity) throws RemoteException;

    void changeQuantityOfItem(UUID releaseId, int newQuantity) throws RemoteException;

    void removeItemFromBasket(UUID releaseId) throws RemoteException;

    List<BasketItemRemoteDTO> itemsInBasket() throws RemoteException;

    int amountOfItemsInBasket() throws RemoteException;


}
