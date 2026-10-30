package com.event.management.repository;

import com.event.management.model.entity.Category;
import com.event.management.model.entity.Event;
import com.event.management.model.entity.Location;
import com.event.management.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {
    List<Event> searchByTitleContainingIgnoreCase(String title);
    List<Event> findByCategory_Id(Long categoryId);
    List<Event> findByOrganizer_Id(Long organizerId);
    List<Event> findByEventDate(LocalDateTime eventDate);
    List<Event> findByLocation_Id(Long locationId);
}