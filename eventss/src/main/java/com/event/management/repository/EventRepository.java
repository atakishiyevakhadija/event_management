package com.event.management.repository;

import com.event.management.model.entity.Category;
import com.event.management.model.entity.Event;
import com.event.management.model.entity.Location;
import com.event.management.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> searchByTitleContainingIgnoreCase(String title);
    List<Event> findByCategory(Category category);
    List<Event> findByOrganizer(User organizer);
    List<Event> findByEventDate(LocalDateTime eventDate);
    List<Event> findByLocation(Location location);
}