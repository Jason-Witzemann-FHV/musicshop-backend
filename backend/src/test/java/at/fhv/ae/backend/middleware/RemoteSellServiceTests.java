package at.fhv.ae.backend.middleware;

import at.fhv.ae.backend.application.SellService;
import at.fhv.ae.shared.rmi.RemoteSellService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class RemoteSellServiceTests {

    private RemoteSellService remoteSellService;
    private SellService sellService;

    @BeforeEach
    void setupMocksAndTestClass() throws RemoteException {
        sellService = mock(SellService.class);
        remoteSellService = new RemoteSellServiceImpl(sellService);
    }

    @Test
    void given_nothing_when_sell_then_application_service_executed() throws RemoteException {
        remoteSellService.sellItemsInBasket();
        verify(sellService).sellItemsInBasket();
    }

}
