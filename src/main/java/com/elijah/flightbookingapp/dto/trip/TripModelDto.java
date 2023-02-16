package com.elijah.flightbookingapp.dto.trip;

import com.elijah.flightbookingapp.model.customer.Customer;
import com.elijah.flightbookingapp.model.route.RouteModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripModelDto {

    private int routeId;
    private String customerEmail;
    private LocalDate travelDate;
}
