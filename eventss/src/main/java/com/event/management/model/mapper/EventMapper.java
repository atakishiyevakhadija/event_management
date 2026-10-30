package com.event.management.model.mapper;

import com.event.management.model.dto.EventDTO;
import com.event.management.model.entity.Event;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {
    public EventDTO mapToDto(Event event){
        EventDTO dto = new EventDTO();
        dto.setId(event.getId());
        dto.setTitle(event.getTitle());
        dto.setDescription(event.getDescription());
        dto.setCapacity(event.getCapacity());
        dto.setPrice(event.getPrice());
        dto.setEventDate(event.getEventDate());
        dto.setCreatedAt(event.getCreatedAt());
        dto.setUpdatedAt(event.getUpdatedAt());

        dto.setCategoryId(event.getCategory().getId());
        dto.setCategoryName(event.getCategory().getName());

        dto.setLocationId(event.getLocation().getId());
        dto.setLocationName(event.getLocation().getName());

        if (event.getOrganizer() != null) {
            dto.setOrganizerId(event.getOrganizer().getId());
        }

        return dto;
    }
}