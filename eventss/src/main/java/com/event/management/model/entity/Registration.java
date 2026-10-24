package com.event.management.model.entity;

import com.event.management.model.enums.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity(name = "registration")
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
    @Column(nullable = false)
    private LocalDateTime registrationDate;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    public Registration(Long id, User user, Event event, LocalDateTime registrationDate, Status status) {
        this.id = id;
        this.user = user;
        this.event = event;
        this.registrationDate = registrationDate;
        this.status = status;
    }
}