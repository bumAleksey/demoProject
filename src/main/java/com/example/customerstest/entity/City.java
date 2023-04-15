package com.example.customerstest.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String country;
    @Column(nullable = false)
    private double latitude;
    @Column(nullable = false)
    private double longitude;
    private boolean isMain;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_login", referencedColumnName = "login")
    private User user;

    public City(String name, String country, double latitude, double longitude, boolean isMain, User user) {
        this.name = name;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isMain = isMain;
        this.user = user;
    }
    public City() {

    }
}
