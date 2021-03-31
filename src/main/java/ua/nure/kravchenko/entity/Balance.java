package ua.nure.kravchenko.entity;

import lombok.Data;

import javax.persistence.*;

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
}































































