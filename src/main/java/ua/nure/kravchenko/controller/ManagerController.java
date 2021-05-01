package ua.nure.kravchenko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.nure.kravchenko.entity.project.Payment;
import ua.nure.kravchenko.requests_params.LocationFindAdressReq;
import ua.nure.kravchenko.requests_params.LocationRequest;
import ua.nure.kravchenko.entity.Balance;
import ua.nure.kravchenko.entity.Location;
import ua.nure.kravchenko.entity.UserEntity;
import ua.nure.kravchenko.entity.dto.UserDTO;
import ua.nure.kravchenko.entity.project.Roles;
import ua.nure.kravchenko.entity.project.Statistic;
import ua.nure.kravchenko.requests_params.NewCardRequest;
import ua.nure.kravchenko.service.BalanceService;
import ua.nure.kravchenko.service.LocationService;
import ua.nure.kravchenko.service.PaymentService;
import ua.nure.kravchenko.service.UserService;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/manager")
public class ManagerController {
    private final UserService userService;
    private final LocationService locationService;
    private final BalanceService balanceService;
    private final PaymentService paymentService;

    @Autowired
    public ManagerController(UserService userService, LocationService locationService, BalanceService balanceService, PaymentService paymentService) {
        this.userService = userService;
        this.locationService = locationService;
        this.balanceService = balanceService;
        this.paymentService = paymentService;
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

    @PostMapping("/card/{id}")
    public Balance createBalanceCard(@PathVariable int id, @RequestBody NewCardRequest newCardRequest){
        UserEntity user = userService.findById(id);
        Balance balance = new Balance();
        balance.setSalary(newCardRequest.getSalary());
        balance.setBalance(newCardRequest.getBalance());
        balance.setCard(newCardRequest.getCard());
        balance.setUser(user);
        user.setBalance(balance);
        userService.saveUser(user);
        return user.getBalance();
    }

    @PostMapping("/users/{id}/{id2}/accept")
    public Balance acceptPaymentRequest(@PathVariable int id, @PathVariable int id2) throws Exception {
        return userService.acceptPaymentRequest(id, id2);
    }

    @PostMapping("/users/{id}/decline")
    public Balance declinePaymentRequest(@PathVariable int id) {
        UserEntity user = userService.findById(id);
        if (userService.checkBalanceExist(user)) {
            Payment paymentWorker = new Payment();
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            paymentWorker.setDate(timestamp);
            paymentWorker.setBalance(user.getBalance());
            paymentWorker.setAcceptStatus(false);
            paymentWorker.setMoney(user.getBalance().getRequest());
            paymentService.save(paymentWorker);
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

    @GetMapping("/balance/{id}")
    public Balance getManagerBalance(@PathVariable int id){
        UserEntity user = userService.findById(id);
        if(user.getBalance()!=null){
            return user.getBalance();
        }
        return new Balance();
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

    @GetMapping("/balance/payments/{id}")
    public List<Payment> getPayments(@PathVariable int id){
        UserEntity user = userService.findById(id);
        return user.getBalance().getPayments();
    }



//    @PatchMapping("/edit")
//    public UserDTO editProfile(@PathVariable int id) throws Exception {
//        UserEntity userEntity = userService.deleteUserLocation(id);
//        return new UserDTO(userEntity);
//    }
}