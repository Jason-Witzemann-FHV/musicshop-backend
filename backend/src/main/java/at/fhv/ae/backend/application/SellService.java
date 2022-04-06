package at.fhv.ae.backend.application;

public interface SellService {

    /**
     * @return true if sale was successful. false on unexpected error
     */
    boolean sellItemsInBasket(String userId);


}
