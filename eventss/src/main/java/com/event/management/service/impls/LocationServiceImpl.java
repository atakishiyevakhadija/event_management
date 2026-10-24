package com.event.management.service.impls;

import com.event.management.model.dto.CreateLocationRequest;
import com.event.management.model.dto.LocationDTO;
import com.event.management.model.dto.UpdateLocationRequest;
import com.event.management.model.entity.Location;
import com.event.management.model.mapper.LocationMapper;
import com.event.management.repository.LocationRepository;
import com.event.management.service.interfaces.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;


    @Override
    public LocationDTO create(CreateLocationRequest request) {
        Location location = new Location();
        location.setName(request.getName());
        location.setCity(request.getCity());
        location.setAddress(request.getAddress());
        location.setCapacity(request.getCapacity());

        Location saved = locationRepository.save(location);

        return locationMapper.mapToDto(saved);
    }

    @Override
    public LocationDTO update(Long id, UpdateLocationRequest updateRequest) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found!"));
        location.setName(updateRequest.getName());
        location.setCity(updateRequest.getCity());
        location.setAddress(updateRequest.getAddress());
        location.setCapacity(updateRequest.getCapacity());

        Location updated = locationRepository.save(location);

        return locationMapper.mapToDto(updated);
    }

    @Override
    public Page<LocationDTO> getAll(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Location> locations = locationRepository.findAll(pageable);

        return locations.map(locationMapper::mapToDto);
    }

    @Override
    public LocationDTO getById(Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found!"));

        return locationMapper.mapToDto(location);
    }

    @Override
    public void delete(Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found!"));
        locationRepository.delete(location);
    }
}