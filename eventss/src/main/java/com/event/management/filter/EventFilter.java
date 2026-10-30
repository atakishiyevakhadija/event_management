package com.event.management.filter;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventFilter {
    private String title;
    private String description;
    private Long capacity;
    private Double minPrice;
    private Double maxPrice;
    private LocalDateTime eventDate;
}