package ua.nure.kravchenko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.nure.kravchenko.controller.requests_params.LocationFindAdressReq;
import ua.nure.kravchenko.controller.requests_params.LocationRequest;
import ua.nure.kravchenko.entity.Location;
import ua.nure.kravchenko.entity.UserEntity;
import ua.nure.kravchenko.entity.project.Roles;
import ua.nure.kravchenko.entity.project.Statistic;
import ua.nure.kravchenko.service.LocationService;
import ua.nure.kravchenko.service.UserService;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/manager")
public class ManagerController {
    @Autowired
    private UserService userService;
    @Autowired
    private LocationService locationService;

    @GetMapping("/users")
    public List<UserEntity> getUsers(){
        return userService.findListByRoleName(Roles.ROLE_USER.getName());
    }

    @PostMapping("/users/{id}/location")
    public UserEntity setLocationToUser(@PathVariable int id, @RequestBody LocationFindAdressReq address) throws Exception {
        Location location = locationService.findByAddress(address.getAddress());
        UserEntity user;
        Optional<UserEntity> userEntityOptional = userService.findById(id);
        if(userEntityOptional.isPresent() ){
            user = userEntityOptional.get();
            if(user.getRoleEntity().getName().equals(Roles.ROLE_USER.getName()) & location != null){
                user.setLocation(location);
                return userService.updateUser(user);
            }else{
                throw new Exception("No such user with role USER_ROLE with this id");
            }
        }else{
            throw new Exception("No such user with this id");
        }
    }

    @GetMapping("/users/{id}")
    public UserEntity getUser(@PathVariable int id) throws Exception {
        UserEntity user;
        Optional<UserEntity> userEntityOptional = userService.findById(id);
        if(userEntityOptional.isPresent()){
            user = userEntityOptional.get();
            if(user.getRoleEntity().getName().equals(Roles.ROLE_USER.getName())){
                return user;
            }else{
                throw new Exception("No such user with role USER_ROLE with this id");
            }
        }else {
            throw new Exception("No such user with this id");
        }
    }

    @PostMapping("/locations/creation")
    public Location createLocation(@RequestBody LocationRequest location){
        Location loc = new Location(location.getAddress(), location.getFloor(), location.getRoom(), location.getSquare());
        return  locationService.saveLocation(loc);
    }

    @GetMapping("/locations/{id}")
    public Location getLocation(@PathVariable int id){
        List<Location> loc = locationService.findAllById(id);
        Location location = new Location();
        if(!loc.isEmpty()){
            location = loc.get(0);
        }
        return location;
    }

    @GetMapping("/statistics/{id}")
    public Statistic getDailyStatistics(@PathVariable int id){
        List<Location> locationList = locationService.findAllById(id);
        Location location = locationList.get(0);
        return locationService.getDailyStatistics(location);
    }
}