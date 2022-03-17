package at.fhv.ae.backend;

import at.fhv.ae.backend.domain.repository.BasketRepository;
import at.fhv.ae.backend.infrastructure.ArrayListBasketRepository;

public class ServiceRegistry {

    private ServiceRegistry() { } // hide public constructor

    private static BasketRepository basketRepository;

    public static BasketRepository basketRepository() {
        if (basketRepository == null) {
            basketRepository = new ArrayListBasketRepository();
        }
        return basketRepository;
    }


}
