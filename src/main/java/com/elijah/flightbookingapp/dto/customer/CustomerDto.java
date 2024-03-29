package com.elijah.flightbookingapp.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    private String name;
    private String address;
    private String gender;
    private String phoneNumber;
    private String email;
    private String password;
    private LocalDate dateOfBirth;
    private String stateOfOrigin;
    private String localGovernment;
    private String profileImage;
}

