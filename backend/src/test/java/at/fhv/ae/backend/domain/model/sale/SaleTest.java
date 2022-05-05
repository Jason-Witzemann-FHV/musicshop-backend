package at.fhv.ae.backend.domain.model.sale;

import at.fhv.ae.backend.domain.model.release.ReleaseId;
import at.fhv.ae.backend.domain.model.user.UserId;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

public class SaleTest {

    @Test
    public void given_some_sale_when_returning_some_items_update_returned_items() {

        var sale = Sale.create(
                new SaleId(1),
                new UserId("tf-test"),
                new ObjectId(),
                PaymentType.CREDIT_CARD,
                SaleType.IN_PERSON,
                List.of(new Item(new ReleaseId(UUID.randomUUID()), 7, 2.5))
        );

        sale.returnItems(sale.items().get(0).releaseId(), 6);

        Assertions.assertEquals(6, sale.items().get(0).nrOfReturnedItems());
    }

    @Test
    public void given_some_sale_when_returning_too_many_items_throw_Exception() {

        var sale = Sale.create(
                new SaleId(1),
                new UserId("tf-test"),
                new ObjectId(),
                PaymentType.CREDIT_CARD,
                SaleType.IN_PERSON,
                List.of(new Item(new ReleaseId(UUID.randomUUID()), 7, 2.5))
        );

        Assertions.assertThrows(IllegalArgumentException.class, () -> sale.returnItems(sale.items().get(0).releaseId(), 8));
    }

}
