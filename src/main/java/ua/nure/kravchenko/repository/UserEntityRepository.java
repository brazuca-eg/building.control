package ua.nure.kravchenko.repository;


import ua.nure.kravchenko.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserEntityRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByLogin(String login);
    List<UserEntity> findAll();
    List<UserEntity> findUserEntitiesByRoleEntityName(String roleName);
}
