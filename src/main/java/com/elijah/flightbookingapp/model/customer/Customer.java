package com.elijah.flightbookingapp.model.customer;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String address;
    private String gender;
    private String phoneNumber;
    private String email;
    private String password;
    private LocalDate dateOfBirth;
    private Integer age;
    private Integer approvedTrip;
    private Integer cancelledTrip;
    private Integer pendingTrip;
    private String stateOfOrigin;
    private String localGovernment;
    private String profileImage;
}
