package com.elijah.flightbookingapp.dto.route;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
public class RouteDto {

    private String takeOffLocation;
    private String destination;
    private String destinationAbbreviation;
    private String takeOffAbbreviation;
    private String flightDuration;

    public String getFlightDuration() {
        return flightDuration;
    }

    public void setFlightDuration(String flightDuration) {
        this.flightDuration = flightDuration;
    }

    public String getDestinationAbbreviation() {
        return destinationAbbreviation;
    }

    public void setDestinationAbbreviation(String destinationAbbreviation) {
        this.destinationAbbreviation = destinationAbbreviation;
    }

    public String getTakeOffAbbreviation() {
        return takeOffAbbreviation;
    }

    public void setTakeOffAbbreviation(String takeOffAbbreviation) {
        this.takeOffAbbreviation = takeOffAbbreviation;
    }

    @Temporal(TemporalType.TIME)
    @DateTimeFormat(style = "hh:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "hh:mm")
    private Date takeOffTime;

    @Temporal(TemporalType.TIME)
    @DateTimeFormat(style = "hh:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "hh:mm")
    private Date arrivalTime;
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
