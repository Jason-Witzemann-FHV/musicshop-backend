package at.fhv.ae.backend;

import at.fhv.ae.backend.application.*;
import at.fhv.ae.backend.application.impl.*;
import at.fhv.ae.backend.domain.repository.*;
import at.fhv.ae.backend.infrastructure.*;
import at.fhv.ae.backend.middleware.common.CredentialsService;
import at.fhv.ae.shared.repository.CustomerRepository;
import lombok.SneakyThrows;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.ConnectionFactory;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.rmi.Naming;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

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

    private static NewsPublisherService newsPublisherService;

    // remote customer db services

    private static CustomerRepository customerRepository;

    // messaging

    private static BroadcastService broadcastService;

    private static ConnectionFactory jmsConnectionFactory;

    private static NewsRepository newsRepository;



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
