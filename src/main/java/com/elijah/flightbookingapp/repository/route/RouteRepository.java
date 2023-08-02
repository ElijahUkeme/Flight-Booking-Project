package com.elijah.flightbookingapp.repository.route;

import com.elijah.flightbookingapp.model.route.RouteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<RouteModel,Integer> {

    @Query("select r from RouteModel r where r.takeOffLocation =:takeOffLocation and" +
            " r.destination =:destination")
    List<RouteModel> findAllRouteBasedOnEntry(@Param("takeOffLocation")String takeOffLocation,
                                              @Param("destination")String destination);
}
