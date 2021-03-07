package ua.nure.kravchenko.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "user_table")
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String login;

    @Column
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleEntity roleEntity;

//    @ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private Location location;
}
