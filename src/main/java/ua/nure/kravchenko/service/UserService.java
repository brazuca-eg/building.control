package ua.nure.kravchenko.service;

import org.springframework.web.bind.annotation.PathVariable;
import ua.nure.kravchenko.entity.Balance;
import ua.nure.kravchenko.entity.Location;
import ua.nure.kravchenko.entity.RoleEntity;
import ua.nure.kravchenko.entity.UserEntity;
import ua.nure.kravchenko.entity.dto.UserDTO;
import ua.nure.kravchenko.entity.project.Roles;
import ua.nure.kravchenko.repository.RoleEntityRepository;
import ua.nure.kravchenko.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserEntityRepository userEntityRepository;
    private final RoleEntityRepository roleEntityRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserEntityRepository userEntityRepository, RoleEntityRepository roleEntityRepository, PasswordEncoder passwordEncoder) {
        this.userEntityRepository = userEntityRepository;
        this.roleEntityRepository = roleEntityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity saveUser(UserEntity userEntity) {
        RoleEntity userRole = roleEntityRepository.findByName("ROLE_USER");
        userEntity.setRoleEntity(userRole);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return userEntityRepository.save(userEntity);
    }

    public UserEntity findById(int id) {
        Optional<UserEntity> userEntity = userEntityRepository.findById(id);
        if(userEntity.isPresent()){
            return  userEntity.get();
        }
        return new UserEntity();
    }

    public boolean checkBalanceExist(UserEntity user){
        if(user.getBalance()!=null) {
            user.getBalance().setRequest(0);
            return true;
        }
        return false;
    }

    public UserEntity acceptPaymentRequest(UserEntity userEntity){
        if(checkBalanceExist(userEntity)){
            Balance balance = userEntity.getBalance();
            balance.setBalance(balance.getBalance() + balance.getRequest());
            balance.setRequest(0);
            userEntity.setBalance(balance);
            return userEntity;
        }
        return null;
    }


    public UserEntity setLocationToUser(UserEntity userEntity, Location location){
        if(checkRole(userEntity)==true){
            userEntity.setLocation(location);
        }
        return userEntity;
    }

    public boolean checkRole(UserEntity userEntity){
        if(userEntity.getRoleEntity().getName().equals(Roles.ROLE_USER.getName())){
            return true;
        }
        return false;
    }

    public UserEntity updateUser(UserEntity userEntity) {
        return userEntityRepository.save(userEntity);
    }

    public UserEntity findByLogin(String login) {
        return userEntityRepository.findByLogin(login);
    }

    public UserEntity findByLoginAndPassword(String login, String password) {
        UserEntity userEntity = findByLogin(login);
        if (userEntity != null) {
            if (passwordEncoder.matches(password, userEntity.getPassword())) {
                return userEntity;
            }
        }
        return null;
    }

    public List<UserDTO> findListByRoleName(String roleName) {
        List<UserEntity> userEntities =  userEntityRepository.findUserEntitiesByRoleEntityName(roleName);
        List<UserDTO> users = new ArrayList<>();
        for (UserEntity userEntity : userEntities) {
            users.add(new UserDTO(userEntity));
        }
        return users;
    }

    public List<UserEntity> findAll() {
        return userEntityRepository.findAll();
    }

    public UserEntity deleteUserLocation(int userId) throws Exception {
        Optional<UserEntity> userEntityOptional =  userEntityRepository.findById(userId);
        if(userEntityOptional.isPresent()){
            UserEntity user = userEntityOptional.get();
            user.setLocation(null);
            return userEntityRepository.save(user);
        }else {
            throw new Exception("No such user with this id");
        }
    }

    public Location getLocation(@PathVariable int id){
        Optional<UserEntity> userEntity = userEntityRepository.findById(id);
        if(userEntity.isPresent()){
            UserEntity  user = userEntity.get();
            if(user.getLocation()!=null){
                return user.getLocation();
            }
        }
        return new Location();
    }
}
