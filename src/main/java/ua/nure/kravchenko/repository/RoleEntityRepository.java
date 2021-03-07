package ua.nure.kravchenko.repository;

import ua.nure.kravchenko.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleEntityRepository extends JpaRepository<RoleEntity, Integer> {
    RoleEntity findByName(String name);
}
