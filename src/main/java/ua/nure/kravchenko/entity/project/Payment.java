package ua.nure.kravchenko.entity.project;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import ua.nure.kravchenko.entity.Balance;


import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "payment")
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private Timestamp date;
    @Column
    private double money;
    @Column
    private boolean acceptStatus;
    @ManyToOne
    @JoinColumn(name="balance_id")
    @JsonBackReference
    private Balance balance;


}
