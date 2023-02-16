package com.elijah.flightbookingapp.service.route;

import com.elijah.flightbookingapp.dto.route.RouteDto;
import com.elijah.flightbookingapp.exception.DataNotFoundException;
import com.elijah.flightbookingapp.model.route.RouteModel;
import com.elijah.flightbookingapp.repository.route.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RouteService {
    @Autowired
    private RouteRepository routeRepository;

    public RouteModel saveRoute(RouteDto routeDto) throws ParseException {
        RouteModel routeModel = new RouteModel();
        routeModel.setArrivalTime(routeDto.getArrivalTime());
        routeModel.setDestination(routeDto.getDestination());
        routeModel.setBoardingType(routeDto.getBoardingType());
        routeModel.setPrice(routeDto.getPrice());
        routeModel.setFlightDay(routeDto.getFlightDay());
        routeModel.setTakeOffLocation(routeDto.getTakeOffLocation());
        routeModel.setTakeOffTime(routeDto.getTakeOffTime());

        return routeRepository.save(routeModel);
    }

    public RouteModel findRouteById(Integer id) throws DataNotFoundException {
        if (Objects.isNull(routeRepository.findById(id))){
            throw new DataNotFoundException("There is no route with this id");
        }
        return routeRepository.findById(id).get();
    }
    public RouteModel updateRoute(RouteDto routeDto,Integer id) throws DataNotFoundException, ParseException {
        Optional<RouteModel> routeModel = routeRepository.findById(id);
        if (!routeModel.isPresent()){
            throw new DataNotFoundException("There is no route with this id");
        }
        if (Objects.nonNull(routeDto.getArrivalTime())){
            routeModel.get().setArrivalTime(routeDto.getArrivalTime());
        }
        if (Objects.nonNull(routeDto.getPrice())&& routeDto.getPrice() !=0){
            routeModel.get().setPrice(routeDto.getPrice());
        }
        if (Objects.nonNull(routeDto.getBoardingType())&& !"".equalsIgnoreCase(routeDto.getBoardingType())){
            routeModel.get().setBoardingType(routeDto.getBoardingType());
        }
        if (Objects.nonNull(routeDto.getDestination())&& !"".equalsIgnoreCase(routeDto.getDestination())){
            routeModel.get().setDestination(routeDto.getDestination());
        }if (Objects.nonNull(routeDto.getFlightDay())&& !"".equalsIgnoreCase(routeDto.getFlightDay())){
            routeModel.get().setFlightDay(routeDto.getFlightDay());
        }
        if (Objects.nonNull(routeDto.getTakeOffLocation())&& !"".equalsIgnoreCase(routeDto.getTakeOffLocation())){
            routeModel.get().setTakeOffLocation(routeDto.getTakeOffLocation());
        }if (Objects.nonNull(routeDto.getTakeOffTime())){
            routeModel.get().setTakeOffTime(routeDto.getTakeOffTime());
        }
        return routeRepository.save(routeModel.get());
    }

    public void deleteRouteById(Integer id) throws DataNotFoundException {
        Optional<RouteModel> routeModel = routeRepository.findById(id);
        if (Objects.isNull(routeModel)){
            throw new DataNotFoundException("There is no route with such Id");
        }
        routeRepository.delete(routeModel.get());
    }

    public List<RouteModel> routeModelList(){
        return routeRepository.findAll();
    }
}
