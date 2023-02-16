package com.elijah.flightbookingapp.service.trip;

import com.elijah.flightbookingapp.dto.trip.TripModelDto;
import com.elijah.flightbookingapp.exception.DataNotFoundException;
import com.elijah.flightbookingapp.model.customer.Customer;
import com.elijah.flightbookingapp.model.route.RouteModel;
import com.elijah.flightbookingapp.model.token.FlightToken;
import com.elijah.flightbookingapp.model.trip.TripModel;
import com.elijah.flightbookingapp.repository.trip.TripRepository;
import com.elijah.flightbookingapp.service.customer.CustomerService;
import com.elijah.flightbookingapp.service.route.RouteService;
import com.elijah.flightbookingapp.service.token.FlightTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TripService {
    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private RouteService routeService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private FlightTokenService flightTokenService;

    @Transactional
    public TripModel bookATrip(TripModelDto tripModelDto) throws DataNotFoundException, ParseException {
        RouteModel routeModel = routeService.findRouteById(tripModelDto.getRouteId());
        Customer customer = customerService.getCustomerByEmail(tripModelDto.getCustomerEmail());
        TripModel tripModel = new TripModel();
        tripModel.setCustomer(customer);
        tripModel.setRouteModel(routeModel);
        tripModel.setArrivalTime(routeModel.getArrivalTime());
        tripModel.setDestination(routeModel.getDestination());
        tripModel.setBoardingType(routeModel.getBoardingType());
        tripModel.setPrice(routeModel.getPrice());
        tripModel.setTakeOffLocation(routeModel.getTakeOffLocation());
        tripModel.setTravelDate(tripModelDto.getTravelDate());
        tripModel.setTakeOffTime(tripModel.getTakeOffTime());
        final FlightToken flightToken = new FlightToken(tripModel);
        flightTokenService.saveTripToken(flightToken);
        return tripRepository.save(tripModel);

    }
    public TripModel findTripById(Integer id) throws DataNotFoundException {
        Optional<TripModel> tripModel = tripRepository.findById(id);
        if (Objects.isNull(tripModel)){
            throw new DataNotFoundException("There is no trip with this Id");
        }
        return tripModel.get();
    }
    public List<TripModel> getTripByCustomer(String customerEmail) throws DataNotFoundException {
        Customer customer = customerService.getCustomerByEmail(customerEmail);
        List<TripModel> customerTrips = tripRepository.findByCustomer(customer);
        return customerTrips;
    }
    public List<TripModel> getAllTrips(){
        return tripRepository.findAll();
    }
}
