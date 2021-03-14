package ua.nure.kravchenko.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.nure.kravchenko.entity.Detail;

import java.util.List;

public interface DetailRepository  extends JpaRepository<Detail, Integer> {
    @Override
    List<Detail> findAll();
}
