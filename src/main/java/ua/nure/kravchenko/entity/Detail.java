package ua.nure.kravchenko.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "detail")
@Data
public class Detail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private java.sql.Timestamp datetime;
    @Column(nullable = false)
    private double parameter;
    @Column(nullable = false)
    private int mark;
    @ManyToOne
    @JoinColumn (name="location_id")
    @JsonBackReference
    private Location location;
}
