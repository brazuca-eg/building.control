package ua.nure.kravchenko.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import ua.nure.kravchenko.config.jwt.JwtProvider;
import ua.nure.kravchenko.requests_params.LoginRequest;
import ua.nure.kravchenko.requests_params.RegistrationRequest;
import ua.nure.kravchenko.entity.UserEntity;
import ua.nure.kravchenko.entity.dto.UserDTO;
import ua.nure.kravchenko.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final JwtProvider jwtProvider;

    @Autowired
    public AuthController(UserService userService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/register")
    public UserDTO registerUser(@RequestBody @Valid RegistrationRequest registrationRequest) {
        UserEntity user = new UserEntity();
        user.setPassword(registrationRequest.getPassword());
        user.setLogin(registrationRequest.getLogin());
        user.setName(registrationRequest.getName());
        user.setSurname(registrationRequest.getSurname());
        userService.saveUser(user);
        return new UserDTO(user);
    }

    @PostMapping("/login")
    public UserDTO login(@RequestBody LoginRequest request) {
        UserEntity userEntity = userService.findByLoginAndPassword(request.getLogin(), request.getPassword());
        String token = jwtProvider.generateToken(userEntity.getLogin());
        UserDTO userDTO = new UserDTO(userEntity);
        userDTO.setToken(token);
        return  userDTO;
    }
}
