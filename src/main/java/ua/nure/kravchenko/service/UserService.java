package ua.nure.kravchenko.service;

import ua.nure.kravchenko.entity.RoleEntity;
import ua.nure.kravchenko.entity.UserEntity;
import ua.nure.kravchenko.repository.RoleEntityRepository;
import ua.nure.kravchenko.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserEntityRepository userEntityRepository;
    @Autowired
    private RoleEntityRepository roleEntityRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserEntity saveUser(UserEntity userEntity) {
        RoleEntity userRole = roleEntityRepository.findByName("ROLE_USER");
        userEntity.setRoleEntity(userRole);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return userEntityRepository.save(userEntity);
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

    public  List<UserEntity> findListByRoleName(String roleName){
        return userEntityRepository.findUserEntitiesByRoleEntityName(roleName);
    }

    public List<UserEntity> findAll(){
        List<UserEntity> userEntities = userEntityRepository.findAll();
        return userEntities;
    }
}
