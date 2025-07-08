package com.sanbro.AuthenticationService.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="user_app")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id")
    @SequenceGenerator(name="user_id", sequenceName = "users_id",allocationSize = 50)
    private int id;

    @Column(nullable=false,unique = true)
    private String userName;

    @Column(nullable=false)
    private String fullName;

    @Column(unique = true,nullable = false)
    private String mobileNumber;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value=EnumType.STRING)
    private Roles userRole;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="address_id")
    private Address address;

    @Column(nullable=false)
    private boolean isNotificationsEnabled = false;

}
