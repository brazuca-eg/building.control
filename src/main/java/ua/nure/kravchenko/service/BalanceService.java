package ua.nure.kravchenko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nure.kravchenko.entity.Balance;
import ua.nure.kravchenko.repository.BalanceRepository;

@Service
public class BalanceService {
    @Autowired
    private BalanceRepository balanceRepository;

    public Balance save(Balance balance){
        return balanceRepository.save(balance);
    }
}
