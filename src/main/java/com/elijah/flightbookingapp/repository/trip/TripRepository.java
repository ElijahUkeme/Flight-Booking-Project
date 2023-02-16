package com.elijah.flightbookingapp.repository.trip;

import com.elijah.flightbookingapp.model.customer.Customer;
import com.elijah.flightbookingapp.model.trip.TripModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<TripModel,Integer> {

    List<TripModel> findByCustomer(Customer customer);
}
