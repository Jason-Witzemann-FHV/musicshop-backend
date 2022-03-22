package at.fhv.ae.backend;

import at.fhv.ae.backend.application.BasketService;
import at.fhv.ae.backend.application.ReleaseQueryService;
import at.fhv.ae.backend.application.SellService;
import at.fhv.ae.backend.application.impl.BasketServiceImpl;
import at.fhv.ae.backend.application.impl.ReleaseQueryServiceImpl;
import at.fhv.ae.backend.application.impl.SellServiceImpl;
import at.fhv.ae.backend.domain.repository.BasketRepository;
import at.fhv.ae.backend.domain.repository.ReleaseRepository;
import at.fhv.ae.backend.infrastructure.HashMapBasketRepository;
import at.fhv.ae.backend.infrastructure.HibernateReleaseRepository;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class ServiceRegistry {

    private ServiceRegistry() { } // hide public constructor

    // repositories / persistence

    private static EntityManager entityManager;

    private static BasketRepository basketRepository;

    private static ReleaseRepository releaseRepository;

    // application services

    private static ReleaseQueryService releaseQueryService;

    private static BasketService basketService;

    private static SellService sellService;


    public static EntityManager entityManager() {
        if(entityManager == null) {
            entityManager = Persistence.createEntityManagerFactory("Test").createEntityManager();
        }
        return entityManager;
    }

    public static BasketRepository basketRepository() {
        if (basketRepository == null) {
            basketRepository = new HashMapBasketRepository();
        }
        return basketRepository;
    }

    public static ReleaseRepository releaseRepository() {
        if(releaseRepository == null) {
            releaseRepository = new HibernateReleaseRepository(entityManager());
        }
        return releaseRepository;
    }


    public static ReleaseQueryService releaseQueryService() {
        if(releaseQueryService == null) {
            releaseQueryService = new ReleaseQueryServiceImpl(releaseRepository());
        }
        return releaseQueryService;
    }

    public static BasketService basketService() {
        if(basketService == null) {
            basketService = new BasketServiceImpl(basketRepository(), releaseRepository());
        }
        return basketService;
    }

    public static SellService sellService() {
        if(sellService == null) {
            new SellServiceImpl(null, basketRepository()); // TODO ADD Implementation of Sale-Repo when it is getting implemented.
        }
        return sellService;
    }



}
