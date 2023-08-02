package com.elijah.flightbookingapp.controller.route;

import com.elijah.flightbookingapp.dto.route.RouteDto;
import com.elijah.flightbookingapp.dto.route.RouteSearchDto;
import com.elijah.flightbookingapp.exception.DataNotFoundException;
import com.elijah.flightbookingapp.model.route.RouteModel;
import com.elijah.flightbookingapp.response.ApiResponse;
import com.elijah.flightbookingapp.service.route.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
public class RouteController {
    @Autowired
    private RouteService routeService;

    @PostMapping("/route/add")
    public ResponseEntity<ApiResponse> addARoute(@RequestBody RouteDto routeDto) throws ParseException {
        routeService.saveRoute(routeDto);
        return new ResponseEntity<>(new ApiResponse(true,"Route Added Successfully"), HttpStatus.CREATED);
    }
    @GetMapping("/route/findById")
    public ResponseEntity<RouteModel> getRouteById(@RequestParam("id") Integer id) throws DataNotFoundException {
        return new ResponseEntity<>(routeService.findRouteById(id),HttpStatus.OK);
    }

    @PutMapping("/route/update")
    public ResponseEntity<ApiResponse> updateRoute(@RequestBody RouteDto routeDto,@RequestParam("id")Integer id) throws DataNotFoundException, ParseException {
        routeService.updateRoute(routeDto,id);
        return new ResponseEntity<>(new ApiResponse(true,"Route Updated Successfully"),HttpStatus.OK);
    }
    @DeleteMapping("/route/deleteById")
    public ResponseEntity<ApiResponse> deleteRouteById(@RequestParam("id")Integer id) throws DataNotFoundException {
        routeService.deleteRouteById(id);
        return new ResponseEntity<>(new ApiResponse(true,"Route Deleted Successfully"),HttpStatus.OK);
    }
    @GetMapping("/route/list")
    public ResponseEntity<List<RouteModel>> getAllRoute(){
        return new ResponseEntity<>(routeService.routeModelList(),HttpStatus.OK);
    }

    @PostMapping("/route/search")
    public ResponseEntity<List<RouteModel>> getRouteBasedOnSearch(@RequestBody RouteSearchDto routeSearchDto){
        return new ResponseEntity<>(routeService.routeModelBasedOnSearch(routeSearchDto),HttpStatus.OK);
    }
}
