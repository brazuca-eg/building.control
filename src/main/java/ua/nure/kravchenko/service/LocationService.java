package ua.nure.kravchenko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nure.kravchenko.entity.Location;
import ua.nure.kravchenko.repository.LocationRepository;

import java.util.List;

@Service
public class LocationService {
    @Autowired
    private LocationRepository locationRepository;

    public List<Location> findAllById(Integer id){
        return locationRepository.findAllById(id);
    }

    public Location saveLocation(Location location){
        return locationRepository.save(location);
    }
}
