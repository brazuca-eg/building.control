package ua.nure.kravchenko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.nure.kravchenko.entity.dto.UserDTO;
import ua.nure.kravchenko.entity.project.Payment;
import ua.nure.kravchenko.requests_params.ChangeParams;
import ua.nure.kravchenko.requests_params.NewCardRequest;
import ua.nure.kravchenko.entity.Balance;
import ua.nure.kravchenko.entity.Location;
import ua.nure.kravchenko.entity.UserEntity;
import ua.nure.kravchenko.entity.project.Statistic;
import ua.nure.kravchenko.service.BalanceService;
import ua.nure.kravchenko.service.LocationService;
import ua.nure.kravchenko.service.UserService;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final LocationService locationService;
    private final BalanceService balanceService;

    @Autowired
    public UserController(UserService userService, LocationService locationService, BalanceService balanceService) {
        this.userService = userService;
        this.locationService = locationService;
        this.balanceService = balanceService;
    }

    @GetMapping("/location/{id}")
    public Location getLocation(@PathVariable int id){
        UserEntity user = userService.findById(id);
        if(!user.equals(new UserEntity())){
            return user.getLocation();
        }
        return new Location();
    }

    @GetMapping("/payments/{id}")
    public List<Payment> getPayments(@PathVariable int id){
        UserEntity user = userService.findById(id);
        List<Payment> payments = user.getBalance().getPayments();
        return payments;
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

    @PostMapping("/request/{id}")
    public Balance makeDailRequest(@PathVariable int id){
        UserEntity user =  userService.findById(id);
        Statistic statistic = locationService.getDailyStatistics(user.getLocation());
        Balance balance = userService.generateCompensation(user, statistic);
        user.setBalance(balance);
        userService.saveUser(user);
        return balance;
    }

    @PatchMapping("/change/{id}")
    public UserDTO changeProfile(@PathVariable int id, @RequestBody ChangeParams changeParams){
        UserEntity user =  userService.findById(id);
        user.setName(changeParams.getName());
        user.setSurname(changeParams.getSurname());
        user.setPassword(changeParams.getPassword());
        user.setLogin(changeParams.getLogin());
        userService.saveUser(user);
        return new UserDTO(user);
    }
}
