package com.elijah.flightbookingapp.repository.trip;

import com.elijah.flightbookingapp.model.customer.Customer;
import com.elijah.flightbookingapp.model.trip.TripModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<TripModel,Integer> {

    List<TripModel> findByCustomer(Customer customer);

    @Query("select t from TripModel t where t.customer=:customer and t.status like 'Pending%'")
    List<TripModel> findByCustomerWhereStatusIsPending(@RequestParam Customer customer);


    @Query("select t from TripModel t where t.customer=:customer and t.status like 'Approved%'")
    List<TripModel> findByCustomerWhereStatusIsApproved(@RequestParam Customer customer);


    @Query("select t from TripModel t where t.customer=:customer and t.status like 'Cancelled%'")
    List<TripModel> findByCustomerWhereStatusIsCancelled(@RequestParam Customer customer);

    //@Query("select t from TripModel t where t.travelDate=tripDate")
    List<TripModel> findByTravelDate(LocalDate travelDate);
}
