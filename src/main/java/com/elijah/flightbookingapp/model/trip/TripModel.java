package com.elijah.flightbookingapp.model.trip;

import com.elijah.flightbookingapp.model.customer.Customer;
import com.elijah.flightbookingapp.model.route.RouteModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TripModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String boardingType;
    private String takeOffLocation;
    private String destination;
    private String status;
    private double price;
    private String airplaneNumber;
    private String pilotName;

    public String getPilotName() {
        return pilotName;
    }

    public void setPilotName(String pilotName) {
        this.pilotName = pilotName;
    }

    public String getAirplaneNumber() {
        return airplaneNumber;
    }

    public void setAirplaneNumber(String airplaneNumber) {
        this.airplaneNumber = airplaneNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status =status;
    }



    @Temporal(TemporalType.TIME)
    @DateTimeFormat(style = "hh:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "hh:mm")
    private Date takeOffTime;

    @Temporal(TemporalType.TIME)
    @DateTimeFormat(style = "hh:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "hh:mm")
    private Date arrivalTime;
    private LocalDate travelDate;
    private int seatNumber;
    @OneToOne
    @JoinColumn(name = "route_id")
    private RouteModel routeModel;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBoardingType() {
        return boardingType;
    }

    public void setBoardingType(String boardingType) {
        this.boardingType = boardingType;
    }

    public String getTakeOffLocation() {
        return takeOffLocation;
    }

    public void setTakeOffLocation(String takeOffLocation) {
        this.takeOffLocation = takeOffLocation;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getTakeOffTime() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
        String newDate = dateFormat.format(takeOffTime);
        takeOffTime = dateFormat.parse(newDate);
        return takeOffTime;
    }

    public void setTakeOffTime(Date takeOffTime) {
        this.takeOffTime = takeOffTime;
    }

    public Date getArrivalTime() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
        String newDate = dateFormat.format(arrivalTime);
        arrivalTime = dateFormat.parse(newDate);
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public LocalDate getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(LocalDate travelDate) {
        this.travelDate = travelDate;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public RouteModel getRouteModel() {
        return routeModel;
    }

    public void setRouteModel(RouteModel routeModel) {
        this.routeModel = routeModel;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
