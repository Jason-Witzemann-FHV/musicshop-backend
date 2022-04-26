package at.fhv.ae.backend.middleware;

import at.fhv.ae.backend.application.SellService;
import at.fhv.ae.backend.application.exceptions.OutOfStockException;
import at.fhv.ae.backend.middleware.remoteservices.RemoteSellServiceImpl;
import at.fhv.ae.shared.services.RemoteSellService;
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
        remoteSellService = new RemoteSellServiceImpl(sellService, customerId);
    }

    @Test
    void given_nothing_when_sell_then_application_service_executed() throws RemoteException, OutOfStockException {
        remoteSellService.sellItemsInBasket(null);
        verify(sellService).sellItemsInBasket(customerId, null);
    }

    @Test
    void given_nothing_when_get_sales_of_user_then_application_service_executed() throws RemoteException {
        remoteSellService.salesOfUser();
        verify(sellService).salesOfUser(customerId);
    }

}
