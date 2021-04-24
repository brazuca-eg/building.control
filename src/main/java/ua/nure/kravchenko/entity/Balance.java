package ua.nure.kravchenko.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import ua.nure.kravchenko.entity.project.Payment;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "balance")
@Data
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private double salary;
    @Column
    private int card;
    @Column
    private double balance;
    @Column
    private double request;
    @OneToOne(mappedBy = "balance")
    private UserEntity user;

    @OneToMany(mappedBy = "balance", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    private List<Payment> payments;
}































































