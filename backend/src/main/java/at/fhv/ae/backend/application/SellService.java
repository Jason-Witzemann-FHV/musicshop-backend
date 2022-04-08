package at.fhv.ae.backend.application;

import org.bson.types.ObjectId;

public interface SellService {

    /**
     * @param customerId can be null if sale is anonymous
     * @return true on sell success; false on une
     */
    boolean sellItemsInBasket(String userId, ObjectId customerId);

}
