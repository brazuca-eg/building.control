package ua.nure.kravchenko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nure.kravchenko.entity.project.Payment;
import ua.nure.kravchenko.repository.PaymentRepository;


@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    public Payment save( Payment payment){
        return paymentRepository.save(payment);
    }
}
