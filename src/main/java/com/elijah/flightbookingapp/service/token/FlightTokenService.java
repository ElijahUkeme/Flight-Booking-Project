package com.elijah.flightbookingapp.service.token;

import com.elijah.flightbookingapp.exception.DataNotFoundException;
import com.elijah.flightbookingapp.model.token.FlightToken;
import com.elijah.flightbookingapp.model.trip.TripModel;
import com.elijah.flightbookingapp.repository.token.FlightTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class FlightTokenService {
    @Autowired
    private FlightTokenRepository flightTokenRepository;

    public void saveTripToken(FlightToken flightToken){
        flightTokenRepository.save(flightToken);
    }

    public TripModel getTripInfo(String token) throws DataNotFoundException {
        FlightToken flightToken = flightTokenRepository.findByToken(token);
        if (Objects.isNull(flightToken)){
            throw new DataNotFoundException("There is no flight Associated with this token");
        }
        return flightToken.getTripModel();
    }
}
