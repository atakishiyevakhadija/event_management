package com.event.management.controller;

import com.event.management.model.dto.*;
import com.event.management.model.entity.Category;
import com.event.management.model.entity.Location;
import com.event.management.model.entity.User;
import com.event.management.service.interfaces.EventService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/events")
@Tag(name = "Event Management APIs", description = "APIs for Event management")
@RequiredArgsConstructor
public class EventRestController {
    private final EventService eventService;

    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    @PostMapping
    public EventDTO create(@RequestBody @Valid CreateEventRequest request){
        return eventService.create(request);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    @PutMapping("/{id}")
    public EventDTO update(@PathVariable Long id, @RequestBody @Valid UpdateEventRequest updateRequest){
        return eventService.update(id, updateRequest);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'ORGANIZER')")
    @GetMapping
    public Page<EventDTO> getAll(
            @RequestParam (defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "asc") String  direction
    ){

        return eventService.getAll(page, size, sortBy, direction);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'ORGANIZER')")
    @GetMapping("/{id}")
    public EventDTO getEventById(@PathVariable Long id){
        return eventService.getById(id);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'ORGANIZER')")
    @GetMapping("title/{title}")
    public List<EventDTO> getEventByTitle(@PathVariable String title){
        return eventService.searchByTitle(title);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'ORGANIZER')")
    @GetMapping("category/{category}")
    public List<EventDTO> getEventByCategory(@PathVariable Category category){
        return eventService.getByCategory(category);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'ORGANIZER')")
    @GetMapping("organizer/{organizer}")
    public List<EventDTO> getEventByOrganizer(@PathVariable User organizer){
        return eventService.getByOrganizer(organizer);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'ORGANIZER')")
    @GetMapping("eventDate/{eventDate}")
    public List<EventDTO> getEventByEventDate(@PathVariable LocalDateTime eventDate){
        return eventService.getByEventDate(eventDate);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'ORGANIZER')")
    @GetMapping("/location/{location}")
    public List<EventDTO> getEventByLocation(@PathVariable Location location){
        return eventService.getByLocation(location);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id){
        eventService.delete(id);
    }
}