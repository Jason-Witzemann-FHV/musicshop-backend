package at.fhv.ae.backend.middleware.rmi.services;

import at.fhv.ae.backend.application.SellService;
import at.fhv.ae.backend.application.exceptions.OutOfStockException;
import at.fhv.ae.shared.rmi.RemoteSellService;
import org.bson.types.ObjectId;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteSellServiceImpl extends UnicastRemoteObject implements RemoteSellService {

    private final transient SellService sellService;
    private final String userId;

    public RemoteSellServiceImpl(String userId, SellService sellService) throws RemoteException {
        super();
        this.sellService = sellService;
        this.userId = userId;
    }

    @Override
    public boolean sellItemsInBasket(ObjectId customerId) throws RemoteException {
        try {
            sellService.sellItemsInBasket(userId, customerId);
            return true;
        } catch (OutOfStockException e) {
            return false;
        }
    }
}
