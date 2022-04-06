package at.fhv.ae.backend.middleware;

import at.fhv.ae.backend.application.SellService;
import at.fhv.ae.backend.middleware.rmi.services.RemoteSellServiceImpl;
import at.fhv.ae.shared.rmi.RemoteSellService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class RemoteSellServiceTests {

    private RemoteSellService remoteSellService;
    private String customerId;
    private SellService sellService;

    @BeforeEach
    void setupMocksAndTestClass() throws RemoteException {
        sellService = mock(SellService.class);
        customerId = "nsu3146";
        remoteSellService = new RemoteSellServiceImpl(customerId, sellService);
    }

    @Test
    void given_nothing_when_sell_then_application_service_executed() throws RemoteException {
        remoteSellService.sellItemsInBasket(null);
        verify(sellService).sellItemsInBasket(customerId, null);
    }

}
