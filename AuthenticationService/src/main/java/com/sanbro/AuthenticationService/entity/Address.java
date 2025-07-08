package com.sanbro.AuthenticationService.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"latitude","longitude"}))
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "Address_id")
    @SequenceGenerator(name="Address_id",allocationSize = 50,sequenceName = "Address_id")
    private int id;
    private String latitude;
    private String longitude;
}
