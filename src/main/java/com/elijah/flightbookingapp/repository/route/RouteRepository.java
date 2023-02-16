package com.elijah.flightbookingapp.repository.route;

import com.elijah.flightbookingapp.model.route.RouteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<RouteModel,Integer> {
}
