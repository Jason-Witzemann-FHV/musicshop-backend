package at.fhv.ae.backend;

import at.fhv.ae.shared.repository.CustomerRepository;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.rmi.Naming;

public class ServiceRegistry {



    private ServiceRegistry() { } // hide public constructor

    private static EntityManager entityManager;

    private static CustomerRepository customerRepository;


    public static EntityManager entityManager() {
        if(entityManager == null) {
            entityManager = Persistence.createEntityManagerFactory("Test").createEntityManager();
        }
        return entityManager;
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
