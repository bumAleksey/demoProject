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
    private long latitude;
    @Column(nullable = false)
    private long longitude;
    private boolean isMain;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_login", referencedColumnName = "login")
    private User user;


}
