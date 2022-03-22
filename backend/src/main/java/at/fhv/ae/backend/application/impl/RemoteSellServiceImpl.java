package at.fhv.ae.backend.application.impl;

import at.fhv.ae.backend.application.SellService;
import at.fhv.ae.shared.rmi.RemoteSellService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteSellServiceImpl extends UnicastRemoteObject implements RemoteSellService {

    private final transient SellService sellService;

    public RemoteSellServiceImpl(SellService sellService) throws RemoteException {
        super();
        this.sellService = sellService;
    }

    @Override
    public boolean sellItemsInBasket() throws RemoteException {
        return sellService.sellItemsInBasket();
    }
}
