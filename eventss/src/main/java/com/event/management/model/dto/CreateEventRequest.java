package com.event.management.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEventRequest {
    @NotBlank(message = "Title is required!")
    private String title;
    private String description;
    @Positive(message = "Capacity must be greater than 0!")
    @NotNull(message = "Capacity is required!")
    private Long capacity;
    @Min(value = 0, message = "Price cannot be less than 0!")
    private Double price;
    @NotBlank(message = "Event Date is required!")
    private LocalDateTime eventDate;
    private LocalDateTime createdAt;
    private Long categoryId;
    private Long locationId;
}