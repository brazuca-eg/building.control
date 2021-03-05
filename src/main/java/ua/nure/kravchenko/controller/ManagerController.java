package ua.nure.kravchenko.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
//    @Autowired
//    private RoleEntityRepository roleEntityRepository;

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
}