package ua.nure.kravchenko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.nure.kravchenko.requests_params.BalanceBodyReq;
import ua.nure.kravchenko.entity.Detail;
import ua.nure.kravchenko.entity.Location;
import ua.nure.kravchenko.service.DetailService;
import ua.nure.kravchenko.service.LocationService;

import java.util.List;

@RestController
@RequestMapping("/iot")
public class IOTController {
    private final DetailService detailService;
    private final LocationService locationService;

    @Autowired
    public IOTController(DetailService detailService, LocationService locationService) {
        this.detailService = detailService;
        this.locationService = locationService;
    }

    @PostMapping("/{id}/detail")
    public Detail createLocation(@RequestBody BalanceBodyReq det, @PathVariable int id){
        List<Location> locationList = locationService.findAllById(id);
        Location location = locationList.get(0);
        Detail detail = new Detail();
        detail.setMark(det.getMark());
        detail.setDatetime(det.getDatetime());
        detail.setParameter(det.getParameter());
        if(location!=null){
            detail.setLocation(location);
            detailService.save(detail);
        }
        return detail;
    }
}
