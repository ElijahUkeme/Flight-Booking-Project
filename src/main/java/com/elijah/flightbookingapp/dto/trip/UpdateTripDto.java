package com.elijah.flightbookingapp.dto.trip;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTripDto {

    private String boardingType;
    private String takeOffLocation;
    private String destination;
    private String status;
    private double price;
    private String airplaneNumber;
    private String pilotName;

    private LocalDate travelDate;
    private int sitNumber;

    @Temporal(TemporalType.TIME)
    @DateTimeFormat(style = "hh:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "hh:mm")
    private Date takeOffTime;

    @Temporal(TemporalType.TIME)
    @DateTimeFormat(style = "hh:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "hh:mm")
    private Date arrivalTime;
}
