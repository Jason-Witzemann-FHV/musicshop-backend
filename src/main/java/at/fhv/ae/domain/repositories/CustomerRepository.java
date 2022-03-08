package at.fhv.ae.domain.repositories;

import at.fhv.ae.domain.customer.Customer;

import java.util.Optional;

public interface CustomerRepository {

    Optional<Customer> find(String id);

}
