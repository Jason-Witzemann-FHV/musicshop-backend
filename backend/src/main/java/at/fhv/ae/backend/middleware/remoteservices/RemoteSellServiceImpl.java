package at.fhv.ae.backend.middleware.remoteservices;

import at.fhv.ae.backend.application.SellService;
import at.fhv.ae.backend.application.dto.SaleItemsDTO;
import at.fhv.ae.backend.application.exceptions.OutOfStockException;
import at.fhv.ae.shared.dto.sale.ItemRemoteDTO;
import at.fhv.ae.shared.dto.sale.SaleItemsRemoteDTO;
import at.fhv.ae.shared.services.RemoteSellService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import java.util.List;
import java.util.stream.Collectors;

@Stateful
@NoArgsConstructor
@AllArgsConstructor
public class RemoteSellServiceImpl implements RemoteSellService {

    @EJB
    private SellService sellService;

    private String userId;

    @Override
    public void init(String userId) {
        if (this.userId != null) {
            throw new IllegalStateException("Instance already initialized!");
        }
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
    public List<SaleItemsRemoteDTO> allSales() {
        return sellService.allSales().stream()
                .map(sale -> new SaleItemsRemoteDTO(
                        sale.saleNumber(),
                        sale.dateOfSale(),
                        sale.customerId(),
                        sale.totalPrice(),
                        sale.items().stream().map(item -> new ItemRemoteDTO(item.itemId(), item.title(), item.amount(), item.pricePerItem(),item.numberOfReturnedItems()))
                                .collect(Collectors.toList())
                )).collect(Collectors.toList());
    }

    @Override
    public SaleItemsRemoteDTO searchSale(int saleNum) {
        var result = sellService.searchSale(saleNum);
        if(result.isPresent()) {
            SaleItemsDTO sale = result.get();
            return new SaleItemsRemoteDTO(
                    sale.saleNumber(),
                    sale.dateOfSale(),
                    sale.customerId(),
                    sale.totalPrice(),
                    sale.items()
                            .stream()
                            .map(item -> new ItemRemoteDTO(item.itemId(),
                                    item.title(),
                                    item.amount(),
                                    item.pricePerItem(),
                                    item.numberOfReturnedItems()))
                            .collect(Collectors.toList())
            );
        } else {
            return null;
        }
    }
}
