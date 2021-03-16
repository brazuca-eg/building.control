package ua.nure.kravchenko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.nure.kravchenko.controller.requests_params.NewCardRequest;
import ua.nure.kravchenko.entity.Balance;
import ua.nure.kravchenko.entity.Location;
import ua.nure.kravchenko.entity.UserEntity;
import ua.nure.kravchenko.entity.project.Statistic;
import ua.nure.kravchenko.service.BalanceService;
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
    @Autowired
    private BalanceService balanceService;

    @GetMapping("/location/{id}")
    public Location getLocation(@PathVariable int id){
        Optional<UserEntity> userEntity = userService.findById(id);
        UserEntity user = userEntity.get();
        return user.getLocation();
    }

    @PostMapping("/card/{id}")
    public Balance createBalanceCard(@PathVariable int id, @RequestBody NewCardRequest newCardRequest){
        Optional<UserEntity> userEntity = userService.findById(id);
        UserEntity user = userEntity.get();
        Balance balance = new Balance();
        balance.setBalance(newCardRequest.getBalance());
        balance.setCard(newCardRequest.getCard());
        balance.setUser(user);
        user.setBalance(balance);
        userService.saveUser(user);
        return balance;
    }

    @PostMapping("/request/{id}")
    public Balance makeDailRequest(@PathVariable int id){
        Optional<UserEntity> userEntity = userService.findById(id);
        UserEntity user = userEntity.get();
        Statistic statistic = locationService.getDailyStatistics(user.getLocation());
        Balance balance = user.getBalance();
        balance.setRequest(balance.getRequest() + statistic.getMarkAverage());
        userService.saveUser(user);
        return balance;
    }


}
