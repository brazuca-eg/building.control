package ua.nure.kravchenko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.nure.kravchenko.controller.requests_params.RequestEmail;
import ua.nure.kravchenko.entity.RoleEntity;
import ua.nure.kravchenko.entity.UserEntity;
import ua.nure.kravchenko.entity.project.Roles;
import ua.nure.kravchenko.repository.RoleEntityRepository;
import ua.nure.kravchenko.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleEntityRepository roleEntityRepository;

    @GetMapping("/users")
    public List<UserEntity> showAllManagers(){
        return userService.findListByRoleName(Roles.ROLE_MANAGER.getName());
    }

    @PostMapping("/users/role")
    public UserEntity changeToManager(@RequestBody RequestEmail login){
        UserEntity userEntity = userService.findByLogin(login.getLogin());
        if(userEntity !=null && userEntity.getRoleEntity().getName().equals("ROLE_USER")){
            RoleEntity roleEntity = roleEntityRepository.findByName("ROLE_MANAGER");
            userEntity.setRoleEntity(roleEntity);
            userService.updateUser(userEntity);
        }
        return userEntity;
    }
}
