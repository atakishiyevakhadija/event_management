package com.event.management.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateLocationRequest {
    @NotBlank(message = "Name is required!")
    private String name;
    @NotBlank(message = "City is required!")
    private String city;
    @NotBlank(message = "City is required!")
    private String address;
    @Positive(message = "Capacity must be greater than 0!")
    @NotNull(message = "Capacity is required!")
    private Long capacity;
}