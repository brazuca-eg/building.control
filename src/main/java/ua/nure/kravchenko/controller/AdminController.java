package ua.nure.kravchenko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.nure.kravchenko.requests_params.BalanceRes;
import ua.nure.kravchenko.requests_params.RequestEmail;
import ua.nure.kravchenko.entity.RoleEntity;
import ua.nure.kravchenko.entity.UserEntity;
import ua.nure.kravchenko.entity.dto.UserDTO;
import ua.nure.kravchenko.entity.project.Roles;
import ua.nure.kravchenko.repository.RoleEntityRepository;
import ua.nure.kravchenko.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleEntityRepository roleEntityRepository;

    @Autowired
    public AdminController(UserService userService, RoleEntityRepository roleEntityRepository) {
        this.userService = userService;
        this.roleEntityRepository = roleEntityRepository;
    }

    @GetMapping("/users")
    public List<UserDTO> showAllManagers() {
        return userService.findListByRoleName(Roles.ROLE_MANAGER.getName());
    }

    @PostMapping("/users/role")
    public UserDTO changeToManager(@RequestBody RequestEmail login) {
        UserEntity userEntity = userService.findByLogin(login.getLogin());
        if (userEntity != null && userEntity.getRoleEntity().getName().equals("ROLE_USER")) {
            RoleEntity roleEntity = roleEntityRepository.findByName("ROLE_MANAGER");
            userEntity.setRoleEntity(roleEntity);
            userService.updateUser(userEntity);
        }
        return new UserDTO(userEntity);
    }

    @GetMapping("/{id}/balance")
    public BalanceRes showAllManagers(@PathVariable int id) {
        UserEntity user;
        BalanceRes balanceRes = new BalanceRes();
        user = userService.findById(id);
        balanceRes.setBalance(user.getBalance().getBalance());
        balanceRes.setCard(user.getBalance().getCard());
        return balanceRes;
    }
}
