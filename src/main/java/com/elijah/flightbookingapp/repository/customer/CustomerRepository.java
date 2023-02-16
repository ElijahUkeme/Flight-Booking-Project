package com.elijah.flightbookingapp.repository.customer;

import com.elijah.flightbookingapp.model.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {

    Customer findByEmail(String email);
    Customer findByPhoneNumber(String phoneNumber);
}
