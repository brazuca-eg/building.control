package ua.nure.kravchenko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.nure.kravchenko.entity.Detail;
import ua.nure.kravchenko.entity.Location;
import ua.nure.kravchenko.service.DetailService;
import ua.nure.kravchenko.service.LocationService;

import java.util.List;

@RestController
@RequestMapping("/iot")
public class IOTController {
    @Autowired
    private DetailService detailService;
    @Autowired
    private LocationService locationService;

    @PostMapping("/{id}/detail")
    public Detail createLocation(@RequestBody Detail detail, @PathVariable int id){
        List<Location> locationList = locationService.findAllById(id);
        Location location = locationList.get(0);
        if(location!=null){
            detail.setLocation(location);
            detailService.save(detail);
        }
        return detail;
    }
}
