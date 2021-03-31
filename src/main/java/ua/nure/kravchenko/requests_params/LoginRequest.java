package ua.nure.kravchenko.requests_params;

import lombok.Data;

@Data
public class LoginRequest {
    private String login;
    private String password;
}
