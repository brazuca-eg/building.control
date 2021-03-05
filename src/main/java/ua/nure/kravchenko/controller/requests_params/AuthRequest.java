package ua.nure.kravchenko.controller.requests_params;

import lombok.Data;

@Data
public class AuthRequest {
    private String login;
    private String password;
}
