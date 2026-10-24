package com.event.management.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    private String description;
    @Column(nullable = false)
    private Long capacity;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false)
    private LocalDateTime eventDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private User organizer;

    public Event(Long id, String title, String description, Long capacity, Double price, LocalDateTime eventDate, LocalDateTime createdAt, LocalDateTime updatedAt, Category category, Location location, User organizer) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.price = price;
        this.eventDate = eventDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.category = category;
        this.location = location;
        this.organizer = organizer;
    }

    public Event(){

    }
}