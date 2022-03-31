package at.fhv.ae.backend;

import at.fhv.ae.backend.application.BasketService;
import at.fhv.ae.backend.application.ReleaseSearchService;
import at.fhv.ae.backend.application.impl.BasketServiceImpl;
import at.fhv.ae.backend.application.impl.ReleaseServiceImpl;
import at.fhv.ae.backend.application.SellService;
import at.fhv.ae.backend.application.impl.SellServiceImpl;
import at.fhv.ae.backend.domain.repository.*;
import at.fhv.ae.backend.infrastructure.*;
import at.fhv.ae.backend.middleware.common.CredentialsService;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.function.Function;

public class ServiceRegistry {


    private ServiceRegistry() { } // hide public constructor

    // repositories / persistence

    private static EntityManager entityManager;

    private static BasketRepository basketRepository;

    private static ReleaseRepository releaseRepository;

    private static SaleRepository saleRepository;

    private static WorkRepository workRepository;

    private static UserRepository userRepository;


    // application services

    private static ReleaseSearchService releaseService;

    private static BasketService basketService;

    private static SellService sellService;

    private static CredentialsService credentialsService;


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

    public static SaleRepository saleRepository() {
        if (saleRepository == null) {
            saleRepository = new HibernateSaleRepository(entityManager());
        }
        return saleRepository;
    }

    public static WorkRepository workRepository() {
        if(workRepository == null) {
            workRepository = new HibernateWorkRepository(entityManager());
        }
        return workRepository;
    }

    public static UserRepository userRepository() {
        if(userRepository == null) {
            userRepository = new HibernateUserRepository(entityManager());
        }
        return userRepository;
    }


    public static ReleaseSearchService releaseService() {
        if(releaseService == null) {
            releaseService = new ReleaseServiceImpl(releaseRepository(), workRepository());
        }
        return releaseService;
    }

    public static BasketService basketService() {
        if(basketService == null) {
            basketService = new BasketServiceImpl(basketRepository(), releaseRepository());
        }
        return basketService;
    }

    public static SellService sellService() {
        if(sellService == null) {
            sellService = new SellServiceImpl(saleRepository(), basketRepository(), entityManager());
        }
        return sellService;
    }

    public static CredentialsService authorizationService() {
        if(credentialsService == null) {
            // TODO add name mapping function after setting up LDAP
            credentialsService = new LdapCredentialsService(Function.identity());
        }
        return credentialsService;
    }

}
