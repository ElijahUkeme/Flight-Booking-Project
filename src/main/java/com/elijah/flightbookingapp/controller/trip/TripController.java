package com.elijah.flightbookingapp.controller.trip;

import com.elijah.flightbookingapp.dto.trip.TripModelDto;
import com.elijah.flightbookingapp.exception.DataNotFoundException;
import com.elijah.flightbookingapp.model.trip.TripModel;
import com.elijah.flightbookingapp.response.ApiResponse;
import com.elijah.flightbookingapp.service.token.FlightTokenService;
import com.elijah.flightbookingapp.service.trip.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
public class TripController {
    @Autowired
    private FlightTokenService flightTokenService;
    @Autowired
    private TripService tripService;

    @PostMapping("/trip/book")
    public ResponseEntity<ApiResponse> saveTripInfo(@RequestBody TripModelDto tripModelDto) throws DataNotFoundException, ParseException {
        tripService.bookATrip(tripModelDto);
        return new ResponseEntity<>(new ApiResponse(true,"Trip booked Successfully"), HttpStatus.CREATED);
    }
    @GetMapping("/trip/findById/{tripId}")
    public ResponseEntity<TripModel> getTripById(@PathVariable("tripId") Integer tripId) throws DataNotFoundException {
        return new ResponseEntity<>(tripService.findTripById(tripId),HttpStatus.OK);
    }
    @GetMapping("/customer/trips")
    public ResponseEntity<List<TripModel>> getTripsForCustomer(@RequestParam("customerEmail") String customerEmail) throws DataNotFoundException {
        return new ResponseEntity<>(tripService.getTripByCustomer(customerEmail),HttpStatus.OK);
    }

    @GetMapping("/trips/all")
    public ResponseEntity<List<TripModel>> getAllTrips(){
        return new ResponseEntity<>(tripService.getAllTrips(),HttpStatus.OK);
    }
    @GetMapping("/tripByToken")
    public ResponseEntity<TripModel> getTripByToken(@RequestParam("token")String token) throws DataNotFoundException {
        return new ResponseEntity<>(flightTokenService.getTripInfo(token),HttpStatus.OK);
    }
}
