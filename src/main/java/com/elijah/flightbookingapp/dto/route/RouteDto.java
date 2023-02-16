package com.elijah.flightbookingapp.dto.route;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
public class RouteDto {

    private String takeOffLocation;
    private String destination;
    private Date takeOffTime;
    private Date arrivalTime;
    private String flightDay;
    private double price;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getBoardingType() {
        return boardingType;
    }

    public void setBoardingType(String boardingType) {
        this.boardingType = boardingType;
    }

    private String boardingType;

    public String getFlightDay() {
        return flightDay;
    }

    public void setFlightDay(String flightDay) {
        this.flightDay = flightDay;
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

}
