package com.event.management.controller;

import com.event.management.model.dto.*;
import com.event.management.service.interfaces.LocationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/locations")
@Tag(name = "Location Management APIs", description = "APIs for Location management")
@RequiredArgsConstructor
public class LocationRestController {
    private final LocationService locationService;

    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    @PostMapping
    public LocationDTO create(@RequestBody @Valid CreateLocationRequest request){
        return locationService.create(request);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    @PutMapping("/{id}")
    public LocationDTO update(@PathVariable Long id, @RequestBody @Valid UpdateLocationRequest updateRequest){
        return locationService.update(id, updateRequest);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'ORGANIZER')")
    @GetMapping
    public Page<LocationDTO> getAll(
            @RequestParam (defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String  direction
    ){

        return locationService.getAll(page, size, sortBy, direction);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'ORGANIZER')")
    @GetMapping("/{id}")
    public LocationDTO getLocationById(@PathVariable Long id){
        return locationService.getById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    @DeleteMapping("/{id}")
    public void deleteLocation(@PathVariable Long id){
        locationService.delete(id);
    }
}