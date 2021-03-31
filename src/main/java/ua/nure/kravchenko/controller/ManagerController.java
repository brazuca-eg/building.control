package ua.nure.kravchenko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.nure.kravchenko.requests_params.LocationFindAdressReq;
import ua.nure.kravchenko.requests_params.LocationRequest;
import ua.nure.kravchenko.entity.Balance;
import ua.nure.kravchenko.entity.Location;
import ua.nure.kravchenko.entity.UserEntity;
import ua.nure.kravchenko.entity.dto.UserDTO;
import ua.nure.kravchenko.entity.project.Roles;
import ua.nure.kravchenko.entity.project.Statistic;
import ua.nure.kravchenko.service.BalanceService;
import ua.nure.kravchenko.service.LocationService;
import ua.nure.kravchenko.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/manager")
public class ManagerController {
    private final UserService userService;
    private final LocationService locationService;
    private final BalanceService balanceService;

    @Autowired
    public ManagerController(UserService userService, LocationService locationService, BalanceService balanceService) {
        this.userService = userService;
        this.locationService = locationService;
        this.balanceService = balanceService;
    }

    @GetMapping("/users")
    public List<UserDTO> getUsers() {
        return userService.findListByRoleName(Roles.ROLE_USER.getName());
    }

    @PostMapping("/users/{id}/location")
    public UserDTO setLocationToWorker(@PathVariable int id, @RequestBody LocationFindAdressReq address) throws Exception {
        Location location = locationService.findByAddress(address.getAddress());
        UserEntity user = userService.findById(id);
        if (userService.checkRole(user)) {
            userService.setLocationToUser(user, location);
            userService.saveUser(user);
            return new UserDTO(user);
        } else {
            throw new Exception("No such user with role USER_ROLE with this id");
        }
    }

    @GetMapping("/users/{id}")
    public UserDTO getUser(@PathVariable int id) throws Exception {
        UserEntity user = userService.findById(id);
        if (userService.checkRole(user)) {
            return new UserDTO(user);
        } else {
            throw new Exception("No such user with role USER_ROLE with this id");
        }
    }

    @PostMapping("/users/{id}/accept")
    public Balance acceptPaymentRequest(@PathVariable int id) throws Exception {
        UserEntity user = userService.findById(id);
        if (user.getBalance() != null) {
            Balance balance = user.getBalance();
            balance.setBalance(balance.getBalance() + balance.getRequest());
            balance.setRequest(0);
            return balanceService.save(balance);
        }
        return new Balance();
    }

    @PostMapping("/users/{id}/decline")
    public Balance declinePaymentRequest(@PathVariable int id) {
        UserEntity user = userService.findById(id);
        if (userService.checkBalanceExist(user)) {
            return balanceService.save(user.getBalance());
        }
        return new Balance();
    }

    @PostMapping("/locations/creation")
    public Location createLocation(@RequestBody LocationRequest location) {
        Location loc = new Location(location.getAddress(), location.getFloor(), location.getRoom(), location.getSquare());
        return locationService.saveLocation(loc);
    }

    @GetMapping("/locations")
    public List<Location> getAllLocations() {
        return locationService.findAllLocations();
    }


    @GetMapping("/locations/{id}")
    public Location getLocation(@PathVariable int id) {
        return locationService.findById(id);
    }

    @DeleteMapping("/locations/{id}")
    public Boolean deleteLocation(@PathVariable int id) {
        return locationService.deleteLocation(id);
    }

    @GetMapping("/statistics/{id}")
    public Statistic getDailyStatistics(@PathVariable int id) {
        List<Location> locationList = locationService.findAllById(id);
        Location location = locationList.get(0);
        return locationService.getDailyStatistics(location);
    }

    @GetMapping("/users/free")
    public List<UserDTO> findUsersWithoutLocation() {
        List<UserDTO> userDTOS = userService.findListByRoleName(Roles.ROLE_USER.getName());
        return userDTOS.stream().filter(user -> user.getLocation() == null).collect(Collectors.toList());
    }

    @PatchMapping("/users/{id}/location")
    public UserDTO deleteUserLocation(@PathVariable int id) throws Exception {
        UserEntity userEntity = userService.deleteUserLocation(id);
        return new UserDTO(userEntity);
    }
}