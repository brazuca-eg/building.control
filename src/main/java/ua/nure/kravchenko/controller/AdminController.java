package ua.nure.kravchenko.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.nure.kravchenko.entity.UserEntity;
import ua.nure.kravchenko.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<UserEntity> showAllEmployees(){
        List<UserEntity> userEntities = userService.findAll();
        return userEntities;
    }
}
