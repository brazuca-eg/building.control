package ua.nure.kravchenko.controller.requests_params;

import lombok.Data;

@Data
public class BalanceBodyReq {
    private java.sql.Timestamp datetime;
    private double parameter;
    private int mark;
}
