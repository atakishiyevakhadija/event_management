package com.event.management.service.interfaces;

import com.event.management.model.dto.*;
import org.springframework.data.domain.Page;

public interface LocationService {
    LocationDTO create(CreateLocationRequest request);
    LocationDTO update(Long id, UpdateLocationRequest updateRequest);
    Page<LocationDTO> getAll(int page, int size, String sortBy, String direction);
    //    Page<LocationDTO> filteredBooks(int page, int size, String sortBy, String direction, EventFilter filter);
    LocationDTO getById(Long id);
    void delete(Long id);
}