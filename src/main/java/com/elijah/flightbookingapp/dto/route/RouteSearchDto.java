package com.elijah.flightbookingapp.dto.route;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouteSearchDto {

    private String takeOffLocation;
    private String destination;
}
