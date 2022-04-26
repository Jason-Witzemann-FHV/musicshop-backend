package at.fhv.ae.backend;

import at.fhv.ae.backend.domain.repository.*;
import at.fhv.ae.backend.infrastructure.hibernate.HibernateReleaseRepository;
import at.fhv.ae.backend.infrastructure.hibernate.HibernateSaleRepository;
import at.fhv.ae.backend.infrastructure.hibernate.HibernateUserRepository;
import at.fhv.ae.backend.infrastructure.hibernate.HibernateWorkRepository;
import at.fhv.ae.backend.infrastructure.inmemory.HashMapBasketRepository;
import at.fhv.ae.shared.repository.CustomerRepository;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.rmi.Naming;

public class ServiceRegistry {



    private ServiceRegistry() { } // hide public constructor

    // repositories / persistence

    private static EntityManager entityManager;

    private static BasketRepository basketRepository;

    private static ReleaseRepository releaseRepository;

    private static SaleRepository saleRepository;

    private static WorkRepository workRepository;

    private static UserRepository userRepository;


    // remote customer db services
    private static CustomerRepository customerRepository;



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
            releaseRepository = new HibernateReleaseRepository();
        }
        return releaseRepository;
    }

    public static SaleRepository saleRepository() {
        if (saleRepository == null) {
            saleRepository = new HibernateSaleRepository();
        }
        return saleRepository;
    }

    public static WorkRepository workRepository() {
        if(workRepository == null) {
            workRepository = new HibernateWorkRepository();
        }
        return workRepository;
    }

    public static UserRepository userRepository() {
        if(userRepository == null) {
            userRepository = new HibernateUserRepository();
        }
        return userRepository;
    }


    public static CustomerRepository customerRepository() {
        if (customerRepository == null) {
            try {
                customerRepository = (CustomerRepository) Naming.lookup("rmi://localhost:10990/customer-repository"); // todo change url
            } catch (Exception e) {
                e.printStackTrace();
                throw new IllegalStateException("Couldn't connect to Customer DB!");
            }
        }
        return customerRepository;
    }



}
