package ua.nure.kravchenko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.nure.kravchenko.entity.Location;
import ua.nure.kravchenko.entity.UserEntity;
import ua.nure.kravchenko.service.LocationService;
import ua.nure.kravchenko.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private LocationService locationService;

    @GetMapping("/location/{id}")
    public Location getLocation(@PathVariable int id){
        Optional<UserEntity> userEntity = userService.findById(id);
        UserEntity user = userEntity.get();
        return user.getLocation();
    }

}
