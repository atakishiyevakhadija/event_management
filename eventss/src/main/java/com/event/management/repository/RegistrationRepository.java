package com.event.management.repository;

import com.event.management.model.entity.Event;
import com.event.management.model.entity.Registration;
import com.event.management.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    List<Registration> findByUser(User user);
    List<Registration> findByEvent(Event event);
    List<Registration> findByUserAndEvent(User user, Event event);
}