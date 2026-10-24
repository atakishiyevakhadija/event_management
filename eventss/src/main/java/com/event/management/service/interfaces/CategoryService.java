package com.event.management.service.interfaces;

import com.event.management.model.dto.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {
    CategoryDTO create(CreateCategoryRequest request);
    CategoryDTO update(Long id, UpdateCategoryRequest updateRequest);
    Page<CategoryDTO> getAll(int page, int size, String sortBy, String direction);
    //    Page<CategoryDTO> filteredBooks(int page, int size, String sortBy, String direction, EventFilter filter);
    CategoryDTO getById(Long id);
    List<CategoryDTO> getByName(String name);
    void delete(Long id);
}