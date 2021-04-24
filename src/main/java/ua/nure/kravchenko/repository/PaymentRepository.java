package ua.nure.kravchenko.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.nure.kravchenko.entity.project.Payment;

public interface PaymentRepository  extends JpaRepository<Payment, Integer> {
}
