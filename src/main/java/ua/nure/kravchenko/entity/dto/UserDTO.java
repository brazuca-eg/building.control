package ua.nure.kravchenko.entity.dto;

import lombok.Data;
import ua.nure.kravchenko.entity.Balance;
import ua.nure.kravchenko.entity.Location;
import ua.nure.kravchenko.entity.RoleEntity;
import ua.nure.kravchenko.entity.UserEntity;

@Data
public class UserDTO {
    private Integer id;
    private String login;
    private RoleEntity roleEntity;
    private String name;
    private String surname;
    private Location location;
    private Balance balance;
    private String token;

    public UserDTO(UserEntity userEntity){
        this.id  = userEntity.getId();
        this.login=userEntity.getLogin();
        this.roleEntity = userEntity.getRoleEntity();
        this.location=userEntity.getLocation();
        this.balance=userEntity.getBalance();
        this.name = userEntity.getName();
        this.surname = userEntity.getSurname();
    }
}
