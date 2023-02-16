package com.elijah.flightbookingapp.model.token;

import com.elijah.flightbookingapp.model.customer.Customer;
import com.elijah.flightbookingapp.model.trip.TripModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@Data
@Entity
public class FlightToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String token;
    private Date createdDate;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "trip_id")
    private TripModel tripModel;

    public FlightToken(TripModel tripModel){
        this.tripModel = tripModel;
        this.createdDate = new Date();
        this.customer = tripModel.getCustomer();
        this.token = "ELIAIR"+ UUID.randomUUID();
    }
}
