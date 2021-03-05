package ua.nure.kravchenko.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.nure.kravchenko.entity.Location;
import ua.nure.kravchenko.entity.RoleEntity;

import java.util.List;


public interface LocationRepository extends JpaRepository<Location, Integer> {
//    Location findByAdress(String adress);
    List<Location> findAllById(Integer id);
}
