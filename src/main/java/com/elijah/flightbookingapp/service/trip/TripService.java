package com.elijah.flightbookingapp.service.trip;

import com.elijah.flightbookingapp.dto.trip.TripModelDto;
import com.elijah.flightbookingapp.dto.trip.UpdateTripDto;
import com.elijah.flightbookingapp.exception.DataNotFoundException;
import com.elijah.flightbookingapp.model.customer.Customer;
import com.elijah.flightbookingapp.model.route.RouteModel;
import com.elijah.flightbookingapp.model.token.FlightToken;
import com.elijah.flightbookingapp.model.trip.TripInfo;
import com.elijah.flightbookingapp.model.trip.TripModel;
import com.elijah.flightbookingapp.repository.trip.TripRepository;
import com.elijah.flightbookingapp.response.ApiResponse;
import com.elijah.flightbookingapp.response.BookingResponse;
import com.elijah.flightbookingapp.service.customer.CustomerService;
import com.elijah.flightbookingapp.service.route.RouteService;
import com.elijah.flightbookingapp.service.token.FlightTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.time.LocalDate;
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
    public ResponseEntity<BookingResponse> bookATrip(TripModelDto tripModelDto) throws DataNotFoundException, ParseException {
        RouteModel routeModel = routeService.findRouteById(tripModelDto.getRouteId());
        Customer customer = customerService.getCustomerByEmail(tripModelDto.getCustomerEmail());
        TripModel tripModel = new TripModel();
        tripModel.setCustomer(customer);
        tripModel.setRouteModel(routeModel);
        if (!isDateCorrect(tripModelDto.getTravelDate())){
            throw new DataNotFoundException("Your entered Date is either today or it has passed, please check it out");
        }
        tripModel.setArrivalTime(routeModel.getArrivalTime());
        tripModel.setDestination(routeModel.getDestination());
        tripModel.setBoardingType(routeModel.getBoardingType());
        tripModel.setPrice(routeModel.getPrice());
        tripModel.setTakeOffLocation(routeModel.getTakeOffLocation());
        tripModel.setTravelDate(tripModelDto.getTravelDate());
        tripModel.setTakeOffTime(routeModel.getTakeOffTime());
        tripModel.setStatus("Pending Approval");
        tripModel.setAirplaneNumber("No Plane Scheduled yet");
        tripModel.setPilotName("No Pilot Scheduled yet");
        tripModel.setSeatNumber(getTripForTheDay(tripModelDto.getTravelDate()).size()+1);
        final FlightToken flightToken = new FlightToken(tripModel);
        flightTokenService.saveTripToken(flightToken);
        tripRepository.save(tripModel);
        return new ResponseEntity<>(new BookingResponse("Trip booked Successfully",flightToken.getToken()), HttpStatus.CREATED);

    }


    private boolean isDateCorrect(LocalDate localDate){
        if (localDate.isAfter(LocalDate.now())){
            return true;
        }else {
            return false;
        }
    }
    public TripInfo findTripById(Integer id) throws DataNotFoundException, ParseException {
        Optional<TripModel> tripModel = tripRepository.findById(id);
        if (!tripModel.isPresent()){
            throw new DataNotFoundException("There is no trip with this Id");
        }
        TripInfo tripInfo = new TripInfo();
        tripInfo.setDestination(tripModel.get().getDestination());
        tripInfo.setSitNumber(tripModel.get().getSeatNumber());
        tripInfo.setStatus(tripModel.get().getStatus());
        tripInfo.setPrice(tripModel.get().getPrice());
        tripInfo.setBoardingType(tripModel.get().getBoardingType());
        tripInfo.setTravelDate(tripModel.get().getTravelDate());
        tripInfo.setArrivalTime(tripModel.get().getArrivalTime());
        tripInfo.setTakeOffLocation(tripModel.get().getTakeOffLocation());
        tripInfo.setTakeOffTime(tripModel.get().getTakeOffTime());
        return tripInfo;
    }
    public List<TripModel> getTripByCustomer(String customerEmail) throws DataNotFoundException {
        Customer customer = customerService.getCustomerByEmail(customerEmail);
        List<TripModel> customerTrips = tripRepository.findByCustomer(customer);
        return customerTrips;
    }

    public ResponseEntity<ApiResponse> updateTripById(UpdateTripDto updateTripDto, Integer tripId) throws DataNotFoundException {
        Optional<TripModel> tripModel = tripRepository.findById(tripId);
        if (!tripModel.isPresent()){
            throw new DataNotFoundException("There is no trip with the passed Id");
        }
        if (Objects.nonNull(updateTripDto.getSitNumber())){
            tripModel.get().setSeatNumber(updateTripDto.getSitNumber());
        }if (Objects.nonNull(updateTripDto.getStatus())&& !"".equalsIgnoreCase(updateTripDto.getStatus())){
            tripModel.get().setStatus(updateTripDto.getStatus());
        }if (Objects.nonNull(updateTripDto.getArrivalTime())){
            tripModel.get().setArrivalTime(updateTripDto.getArrivalTime());
        }if (Objects.nonNull(updateTripDto.getDestination())&& !"".equalsIgnoreCase(updateTripDto.getDestination())){
            tripModel.get().setDestination(updateTripDto.getDestination());
        }
        tripModel.get().setPrice(updateTripDto.getPrice());
        if (Objects.nonNull(updateTripDto.getBoardingType())&& !"".equalsIgnoreCase(updateTripDto.getBoardingType())){
            tripModel.get().setBoardingType(updateTripDto.getBoardingType());
        }if (Objects.nonNull(updateTripDto.getTravelDate())){
            tripModel.get().setTravelDate(updateTripDto.getTravelDate());
        }if (Objects.nonNull(updateTripDto.getTakeOffLocation())){
            tripModel.get().setTakeOffLocation(updateTripDto.getTakeOffLocation());
        }if (Objects.nonNull(updateTripDto.getTakeOffTime())){
            tripModel.get().setTakeOffTime(updateTripDto.getTakeOffTime());
        }if (Objects.nonNull(updateTripDto.getAirplaneNumber()) && !"".equalsIgnoreCase(updateTripDto.getAirplaneNumber())){
            tripModel.get().setAirplaneNumber(updateTripDto.getAirplaneNumber());
        }if (Objects.nonNull(updateTripDto.getPilotName())&& !"".equalsIgnoreCase(updateTripDto.getPilotName())){
            tripModel.get().setPilotName(updateTripDto.getPilotName());
        }
        tripRepository.save(tripModel.get());
        return new ResponseEntity<>(new ApiResponse(true,"Trip Info updated Successfully"),HttpStatus.OK);

    }

    public ResponseEntity<ApiResponse> updateTripByTripToken(UpdateTripDto updateTripDto, String tripToken) throws DataNotFoundException {
        TripModel tripModel = flightTokenService.getTripInfo(tripToken);
        if (Objects.nonNull(updateTripDto.getSitNumber())){
            tripModel.setSeatNumber(updateTripDto.getSitNumber());
        }if (Objects.nonNull(updateTripDto.getStatus())){
            tripModel.setStatus(updateTripDto.getStatus());
        }if (Objects.nonNull(updateTripDto.getArrivalTime())){
            tripModel.setArrivalTime(updateTripDto.getArrivalTime());
        }if (Objects.nonNull(updateTripDto.getDestination())){
            tripModel.setDestination(updateTripDto.getDestination());
        }if (Objects.nonNull(updateTripDto.getBoardingType())){
            tripModel.setBoardingType(updateTripDto.getBoardingType());
        }if (Objects.nonNull(updateTripDto.getTravelDate())){
            tripModel.setTravelDate(updateTripDto.getTravelDate());
        }if (Objects.nonNull(updateTripDto.getDestination())){
            tripModel.setDestination(updateTripDto.getDestination());
        }if (Objects.nonNull(updateTripDto.getTakeOffLocation())){
            tripModel.setTakeOffLocation(updateTripDto.getTakeOffLocation());
        }if (Objects.nonNull(updateTripDto.getTakeOffTime())){
            tripModel.setTakeOffTime(updateTripDto.getTakeOffTime());
        }if (Objects.nonNull(updateTripDto.getAirplaneNumber()) && !"".equalsIgnoreCase(updateTripDto.getAirplaneNumber())){
            tripModel.setAirplaneNumber(updateTripDto.getAirplaneNumber());
        }if (Objects.nonNull(updateTripDto.getPilotName())&& !"".equalsIgnoreCase(updateTripDto.getPilotName())){
            tripModel.setPilotName(updateTripDto.getPilotName());
        }
        tripRepository.save(tripModel);
        return new ResponseEntity<>(new ApiResponse(true,"Trip Info updated Successfully"),HttpStatus.OK);

    }

    public List<TripModel> getTripForTheDay(LocalDate tripDate) {
        List<TripModel> tripForTheDay = tripRepository.findByTravelDate(tripDate);
        return tripForTheDay;
    }
    public List<TripModel> getAllTrips(){
        return tripRepository.findAll();
    }

}
