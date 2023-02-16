package com.elijah.flightbookingapp.repository.token;

import com.elijah.flightbookingapp.model.token.FlightToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightTokenRepository extends JpaRepository<FlightToken,Integer> {

    FlightToken findByToken(String token);
}
