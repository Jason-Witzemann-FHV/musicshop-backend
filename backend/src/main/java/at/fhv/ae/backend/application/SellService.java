package at.fhv.ae.backend.application;

import at.fhv.ae.backend.application.exceptions.OutOfStockException;
import at.fhv.ae.backend.domain.model.release.ReleaseId;
import org.bson.types.ObjectId;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface SellService {

    /**
     * @param customerId can be null if sale is anonymous
     * @return true on sell success; false on une
     */
    boolean sellItemsInBasket(String userId, ObjectId customerId);

}
