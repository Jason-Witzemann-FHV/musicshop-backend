package at.fhv.ae.backend.domain.model.release;

import at.fhv.ae.backend.domain.model.sale.Item;
import at.fhv.ae.backend.domain.model.work.RecordingId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

public class ReleaseTest {

    @Test
    public void given_someRelease_when_decreaseStock_then_stockIsUpdated() {

        var release = new Release(
                new ReleaseId(UUID.randomUUID()),
                10,
                "Sphärenklänge",
                Medium.VINYL,
                66.6,
                new Label("asdf", "jkl"),
                List.of(new Supplier("Johannes", "Stra0e 10, Bregenz")),
                List.of(new RecordingId(UUID.randomUUID())));

        Assertions.assertEquals(10, release.stock());

        release.decreaseStock(5);

        Assertions.assertEquals(5, release.stock());

        Assertions.assertThrows(Throwable.class, () -> release.decreaseStock(6));
    }

    @Test
    public void increase_Stock_with_return_Items(){
        var release = new Release(
                new ReleaseId(UUID.randomUUID()),
                10,
                "Sphärenklänge",
                Medium.VINYL,
                66.6,
                new Label("asdf", "jkl"),
                List.of(new Supplier("Johannes", "Stra0e 10, Bregenz")),
                List.of(new RecordingId(UUID.randomUUID())));

        Assertions.assertEquals(10, release.stock());

        release.increaseStock(5);

        Assertions.assertEquals(15, release.stock());

    }

}
