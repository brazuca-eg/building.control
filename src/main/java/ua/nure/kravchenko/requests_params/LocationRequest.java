package ua.nure.kravchenko.requests_params;

import lombok.Data;

@Data
public class LocationRequest {
    private String address;
    private int floor;
    private int room;
    private double square;
}
