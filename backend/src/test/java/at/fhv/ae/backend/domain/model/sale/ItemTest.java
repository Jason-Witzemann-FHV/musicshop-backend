package at.fhv.ae.backend.domain.model.sale;

import at.fhv.ae.backend.domain.model.release.ReleaseId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class ItemTest {

    @Test
    public void given_some_item_when_returning_items_then_update_amount() {
        var item = new Item(
                new ReleaseId(UUID.randomUUID()),
                6,
                2.5
        );

        Assertions.assertEquals(6, item.amount());

        item.returnItems(5);

        Assertions.assertEquals(5, item.nrOfReturnedItems());
    }

    @Test
    public void given_some_item_when_returning_too_much_items_then_throw_exception() {
        var item = new Item(
                new ReleaseId(UUID.randomUUID()),
                6,
                2.5
        );

        Assertions.assertEquals(6, item.amount());

        Assertions.assertThrows(IllegalArgumentException.class, () -> item.returnItems(7));
    }
}
