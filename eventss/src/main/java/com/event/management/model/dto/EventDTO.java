package com.event.management.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {
    private Long id;
    private String title;
    private String description;
    private Long capacity;
    private Double price;
    private LocalDateTime eventDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Long categoryId;
    private String categoryName;

    private Long locationId;
    private String locationName;

    private Long organizerId;
    private String organizerUsername;

}