package ua.nure.kravchenko.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.nure.kravchenko.entity.Balance;

public interface BalanceRepository extends JpaRepository<Balance, Integer> {

}
