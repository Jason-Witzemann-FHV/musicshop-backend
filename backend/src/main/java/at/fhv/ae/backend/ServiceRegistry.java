package at.fhv.ae.backend;

import at.fhv.ae.backend.application.BasketService;
import at.fhv.ae.backend.application.GenreInfoService;
import at.fhv.ae.backend.application.ReleaseService;
import at.fhv.ae.backend.application.impl.BasketServiceImpl;
import at.fhv.ae.backend.application.impl.GenreInfoServiceImpl;
import at.fhv.ae.backend.application.impl.ReleaseServiceImpl;
import at.fhv.ae.backend.application.SellService;
import at.fhv.ae.backend.application.impl.SellServiceImpl;
import at.fhv.ae.backend.domain.repository.*;
import at.fhv.ae.backend.infrastructure.*;
import at.fhv.ae.backend.middleware.common.AuthorizationService;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class ServiceRegistry {


    private ServiceRegistry() { } // hide public constructor

    // repositories / persistence

    private static EntityManager entityManager;

    private static BasketRepository basketRepository;

    private static ReleaseRepository releaseRepository;

    private static SaleRepository saleRepository;

    private static WorkRepository workRepository;

    private static PermissionRepository permissionRepository;


    // application services

    private static ReleaseService releaseService;

    private static BasketService basketService;

    private static SellService sellService;

    private static GenreInfoService genreInfoService;

    private static AuthorizationService authorizationService;


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

    public static PermissionRepository permissionRepository() {
        if(permissionRepository == null) {
            permissionRepository = new HibernatePermissionRepository(entityManager());
        }
        return permissionRepository;
    }


    public static ReleaseService releaseService() {
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


    public static GenreInfoService genreInfoService() {
        if(genreInfoService == null) {
            genreInfoService = new GenreInfoServiceImpl();
        }
        return genreInfoService;
    }

    public static AuthorizationService authorizationService() {
        if(authorizationService == null) {
            authorizationService = new LdapAuthorizationService();
        }
        return authorizationService;
    }

}
