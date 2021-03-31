package ua.nure.kravchenko.requests_params;

import lombok.Data;

@Data
public class NewCardRequest {
    private double salary;
    private int card;
    private double balance;
}
