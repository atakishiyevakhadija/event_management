package com.event.management.service.interfaces;

import com.event.management.model.dto.CreateEventRequest;
import com.event.management.model.dto.EventDTO;
import com.event.management.model.dto.UpdateEventRequest;
import com.event.management.model.entity.Category;
import com.event.management.model.entity.Location;
import com.event.management.model.entity.User;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;


public interface EventService {
    EventDTO create(CreateEventRequest request);
    EventDTO update(Long id, UpdateEventRequest updateRequest);
    Page<EventDTO> getAll(int page, int size, String sortBy, String direction);
    //    Page<EventDTO> filteredBooks(int page, int size, String sortBy, String direction, EventFilter filter);
    EventDTO getById(Long id);
    List<EventDTO> searchByTitle(String title);
    List<EventDTO> getByCategory(Category category);
    List<EventDTO> getByOrganizer(User organizer);
    List<EventDTO> getByEventDate(LocalDateTime eventDate);
    List<EventDTO> getByLocation(Location location);
    void delete(Long id);
}