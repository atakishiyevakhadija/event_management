package com.event.management.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String address;
    private Long capacity;

    public Location(Long id, String name, String city, String address, Long capacity) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.address = address;
        this.capacity = capacity;
    }

    public Location(){

    }
}