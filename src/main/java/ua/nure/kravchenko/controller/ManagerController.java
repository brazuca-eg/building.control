package ua.nure.kravchenko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.nure.kravchenko.controller.requests_params.LocationFindAdressReq;
import ua.nure.kravchenko.controller.requests_params.LocationRequest;
import ua.nure.kravchenko.entity.Location;
import ua.nure.kravchenko.entity.UserEntity;
import ua.nure.kravchenko.service.LocationService;
import ua.nure.kravchenko.service.UserService;
import java.util.List;

@RestController
@RequestMapping("/manager")
public class ManagerController {
    @Autowired
    private UserService userService;
    @Autowired
    private LocationService locationService;

    @GetMapping("/users")
    public List<UserEntity> getUsers(){
        List<UserEntity> users = userService.findListByRoleName("ROLE_USER");
        return users;
    }

    @PostMapping("/location/create")
    public Location createLocation(@RequestBody LocationRequest location){
        Location loc = new Location();
        loc.setAdress(location.getAdress());
        loc.setSquare(location.getSquare());
        return  locationService.saveLocation(loc);
    }

    @PostMapping("/users/{id}")
    public String setLocationToUser(@PathVariable int id, @RequestBody LocationFindAdressReq adress){
        Location location = locationService.findByAdress(adress.getAdress());
        UserEntity user = userService.findById(id).get();
        if(user!=null && location!=null){
            user.setLocation(location);
        }
        userService.updateUser(user);
        return user.getLogin();
    }
}