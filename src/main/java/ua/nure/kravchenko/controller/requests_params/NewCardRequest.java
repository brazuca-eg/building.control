package ua.nure.kravchenko.controller.requests_params;

import lombok.Data;

@Data
public class NewCardRequest {
    private int card;
    private double balance;
}
