package ua.nure.kravchenko.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.nure.kravchenko.entity.Location;

import java.util.List;


public interface LocationRepository extends JpaRepository<Location, Integer> {
    @Query(value = "SELECT l FROM Location l WHERE  l.adress = :adress ")
    Location findByAddress(@Param("adress") String adress);

    List<Location> findAllById(Integer id);
}
