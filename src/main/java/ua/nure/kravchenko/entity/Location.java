package ua.nure.kravchenko.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "location")
@Data
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String adress;

    @Column
    private double floor;

    @Column
    private double room;

    @Column
    private double square;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    private List<UserEntity> usersInTheLocation;


    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    private List<Detail> locationDetails;

    public Location() {
    }

    public Location(String address, int floor, int room, double square) {
        this.adress = address;
        this.floor = floor;
        this.room = room;
        this.square = square;
    }

    public void addUser(UserEntity userEntity){
        usersInTheLocation.add(userEntity);
        userEntity.setLocation(this);
    }
}
