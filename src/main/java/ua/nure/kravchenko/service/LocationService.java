package ua.nure.kravchenko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nure.kravchenko.entity.Detail;
import ua.nure.kravchenko.entity.Location;
import ua.nure.kravchenko.entity.project.Statistic;
import ua.nure.kravchenko.repository.LocationRepository;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class LocationService {
    @Autowired
    private LocationRepository locationRepository;

    public List<Location> findAllById(Integer id) {
        return locationRepository.findAllById(id);
    }

    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }

    public Location findByAdress(String adress) {
        return locationRepository.findByAdress(adress);
    }

    public List<Location> findAll(int id) {
        return locationRepository.findAllById(id);
    }

    public Statistic getDailyStatistics(Location location){
        List<Detail> locationDetails = location.getLocationDetails();
        Statistic statistic = new Statistic();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Date date = timestamp;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        double markAverage = 0;
        double parametersAverage = 0;
        int count = 0;
        for (int i = 0; i < locationDetails.size(); i++) {
            Date date1 = locationDetails.get(i).getDatetime();
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(date1);
            if(calendar.get(Calendar.YEAR) == calendar1.get(Calendar.YEAR)
            && calendar.get(Calendar.MONTH) == calendar1.get(Calendar.MONTH)
            && calendar.get(Calendar.DAY_OF_MONTH) == calendar1.get(Calendar.DAY_OF_MONTH)){
                System.out.println(locationDetails.get(i).getMark() + " mark");
                markAverage+=locationDetails.get(i).getMark();
                parametersAverage+=locationDetails.get(i).getParameter();
                count++;
            }
        }
        statistic.setLocation(location);
        statistic.setMarkAverage(markAverage/count);
        statistic.setParametersAverage(parametersAverage/count);
        statistic.setDate(timestamp);
        return statistic;
    }
}
