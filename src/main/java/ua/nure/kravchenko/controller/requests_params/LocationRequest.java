package ua.nure.kravchenko.controller.requests_params;

import lombok.Data;

@Data
public class LocationRequest {
    private String adress;
    private double square;
}
