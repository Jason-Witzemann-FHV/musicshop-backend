package at.fhv.ae.backend.middleware.rmi.services;

import at.fhv.ae.backend.application.SellService;
import at.fhv.ae.backend.application.exceptions.OutOfStockException;
import at.fhv.ae.shared.dto.sale.ItemRemoteDTO;
import at.fhv.ae.shared.dto.sale.SaleItemsRemoteDTO;
import at.fhv.ae.shared.rmi.RemoteSellService;
import org.bson.types.ObjectId;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import java.util.List;
import java.util.stream.Collectors;

@Stateful
public class RemoteSellServiceImpl implements RemoteSellService {

    @EJB
    private SellService sellService;

    private String userId;

    public RemoteSellServiceImpl() {

    }

    public RemoteSellServiceImpl(String userId, SellService sellService) {
        this.sellService = sellService;
        this.userId = userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean sellItemsInBasket(ObjectId customerId) {
        try {
            sellService.sellItemsInBasket(userId, customerId);
            return true;
        } catch (OutOfStockException e) {
            return false;
        }
    }

    @Override
    public List<SaleItemsRemoteDTO> salesOfUser() {
        return sellService.salesOfUser(userId).stream()
                .map(sale -> {
                    return new SaleItemsRemoteDTO(
                            sale.saleNumber(),
                            sale.dateOfSale(),
                            sale.customerId(),
                            sale.totalPrice(),
                            sale.items().stream().map(item -> {
                                return new ItemRemoteDTO(item.title(), item.amount(), item.pricePerItem());
                            }).collect(Collectors.toList())
                    );
                }).collect(Collectors.toList());
    }
}
