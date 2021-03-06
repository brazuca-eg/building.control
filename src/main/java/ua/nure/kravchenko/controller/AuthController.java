package ua.nure.kravchenko.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import ua.nure.kravchenko.config.jwt.JwtProvider;
import ua.nure.kravchenko.controller.requests_params.AuthRequest;
import ua.nure.kravchenko.controller.requests_params.AuthResponse;
import ua.nure.kravchenko.controller.requests_params.RegistrationRequest;
import ua.nure.kravchenko.entity.UserEntity;
import ua.nure.kravchenko.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/register")
    public UserEntity registerUser(@RequestBody @Valid RegistrationRequest registrationRequest) {
        UserEntity user = new UserEntity();
        user.setPassword(registrationRequest.getPassword());
        user.setLogin(registrationRequest.getLogin());
        userService.saveUser(user);
        return user;
    }

    @PostMapping("/login")
    public AuthResponse auth(@RequestBody AuthRequest request) {
        UserEntity userEntity = userService.findByLoginAndPassword(request.getLogin(), request.getPassword());
        String token = jwtProvider.generateToken(userEntity.getLogin());
        return new AuthResponse(token);
    }
}
