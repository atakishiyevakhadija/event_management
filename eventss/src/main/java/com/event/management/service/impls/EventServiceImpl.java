package com.event.management.service.impls;

import com.event.management.exceptions.CategoryNotFoundException;
import com.event.management.exceptions.EventNotFoundException;
import com.event.management.exceptions.LocationNotFoundException;
import com.event.management.exceptions.UserNotFoundException;
import com.event.management.filter.EventFilter;
import com.event.management.filter.EventSpecification;
import com.event.management.model.dto.CreateEventRequest;
import com.event.management.model.dto.EventDTO;
import com.event.management.model.dto.UpdateEventRequest;
import com.event.management.model.entity.Category;
import com.event.management.model.entity.Event;
import com.event.management.model.entity.Location;
import com.event.management.model.entity.User;
import com.event.management.model.mapper.EventMapper;
import com.event.management.repository.CategoryRepository;
import com.event.management.repository.EventRepository;
import com.event.management.repository.LocationRepository;
import com.event.management.repository.UserRepository;
import com.event.management.service.interfaces.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;
    private final EventSpecification eventSpecification;

    @Override
    public EventDTO create(CreateEventRequest request) {
        Event event = new Event();
        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setCapacity(request.getCapacity());
        event.setPrice(request.getPrice());
        event.setEventDate(request.getEventDate());
        event.setCreatedAt(LocalDateTime.now());
        event.setCategory(categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new CategoryNotFoundException("Category not found!")));
        event.setLocation(locationRepository.findById(request.getLocationId()).orElseThrow(() -> new LocationNotFoundException("Location not found!")));
        event.setOrganizer(userRepository.findById(request.getOrganizerId()).orElseThrow(() -> new UserNotFoundException("Organizer not found!")));

        Event saved = eventRepository.save(event);

        return eventMapper.mapToDto(saved);
    }

    @Override
    public EventDTO update(Long id, UpdateEventRequest updateRequest) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event not found!"));
        event.setTitle(updateRequest.getTitle());
        event.setDescription(updateRequest.getDescription());
        event.setCapacity(updateRequest.getCapacity());
        event.setPrice(updateRequest.getPrice());
        event.setEventDate(updateRequest.getEventDate());
        event.setCreatedAt(updateRequest.getCreatedAt());
        event.setUpdatedAt(LocalDateTime.now());
        event.setCategory(categoryRepository.findById(updateRequest.getCategoryId()).orElseThrow(() -> new CategoryNotFoundException("Category not found!")));
        event.setLocation(locationRepository.findById(updateRequest.getLocationId()).orElseThrow(() -> new LocationNotFoundException("Location not found!")));
        event.setOrganizer(userRepository.findById(updateRequest.getOrganizerId()).orElseThrow(() -> new UserNotFoundException("Organizer not found!")));

        Event updated = eventRepository.save(event);

        return eventMapper.mapToDto(updated);
    }

    @Override
    public Page<EventDTO> getAll(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Event> events = eventRepository.findAll(pageable);

        return events.map(eventMapper::mapToDto);
    }

    @Override
    public Page<EventDTO> filteredEvents(int page, int size, String sortBy, String direction, EventFilter filter) {
        Sort sort = direction.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Event> events = eventRepository.findAll(EventSpecification.filterEvents(filter), pageable);
        return events.map(eventMapper::mapToDto);
    }


    @Override
    public EventDTO getById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event not found!"));

        return eventMapper.mapToDto(event);
    }

    @Override
    public List<EventDTO> searchByTitle(String title) {
        List<Event> event = eventRepository.searchByTitleContainingIgnoreCase(title);

        return event.stream()
                .map(eventMapper::mapToDto)
                .toList();
    }

    @Override
    public List<EventDTO> getByCategory(Long categoryId) {
        List<Event> event = eventRepository.findByCategory_Id(categoryId);

        return event.stream()
                .map(eventMapper::mapToDto)
                .toList();
    }

    @Override
    public List<EventDTO> getByOrganizer(Long organizerId) {
        List<Event> event = eventRepository.findByOrganizer_Id(organizerId);

        return event.stream()
                .map(eventMapper::mapToDto)
                .toList();
    }

    @Override
    public List<EventDTO> getByEventDate(LocalDateTime eventDate) {
        List<Event> event = eventRepository.findByEventDate(eventDate);

        return event.stream()
                .map(eventMapper::mapToDto)
                .toList();
    }

    @Override
    public List<EventDTO> getByLocation(Long locationId) {
        List<Event> event = eventRepository.findByLocation_Id(locationId);

        return event.stream()
                .map(eventMapper::mapToDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event not found!"));
        eventRepository.delete(event);
    }
}