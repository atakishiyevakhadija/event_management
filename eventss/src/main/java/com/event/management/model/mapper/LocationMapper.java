package com.event.management.model.mapper;

import com.event.management.model.dto.LocationDTO;
import com.event.management.model.entity.Location;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper {
    public LocationDTO mapToDto(Location location){
        LocationDTO dto = new LocationDTO();
        dto.setId(location.getId());
        dto.setName(location.getName());
        dto.setCity(location.getCity());
        dto.setAddress(location.getAddress());
        dto.setCapacity(location.getCapacity());

        return dto;
    }
}