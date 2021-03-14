package ua.nure.kravchenko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nure.kravchenko.entity.Detail;
import ua.nure.kravchenko.repository.DetailRepository;

import java.util.List;

@Service
public class DetailService {
    @Autowired
    private DetailRepository detailRepository;

    public List<Detail> findAll(){
        return detailRepository.findAll();
    }

    public void save(Detail detail){
        detailRepository.save(detail);
    }
}
