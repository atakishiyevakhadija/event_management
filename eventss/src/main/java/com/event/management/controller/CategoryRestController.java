package com.event.management.controller;

import com.event.management.model.dto.CategoryDTO;
import com.event.management.model.dto.CreateCategoryRequest;
import com.event.management.model.dto.UpdateCategoryRequest;
import com.event.management.service.interfaces.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Category Management APIs", description = "APIs for Category management")
@RequiredArgsConstructor
public class CategoryRestController {
    private final CategoryService categoryService;

    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    @PostMapping
    public CategoryDTO create(@RequestBody @Valid CreateCategoryRequest request){
        return categoryService.create(request);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    @PutMapping("/{id}")
    public CategoryDTO update(@PathVariable Long id, @RequestBody @Valid UpdateCategoryRequest updateRequest){
        return categoryService.update(id, updateRequest);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'ORGANIZER')")
    @GetMapping
    public Page<CategoryDTO> getAll(
            @RequestParam (defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String  direction
    ){

        return categoryService.getAll(page, size, sortBy, direction);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'ORGANIZER')")
    @GetMapping("/{id}")
    public CategoryDTO getCategoryById(@PathVariable Long id){
        return categoryService.getById(id);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'ORGANIZER')")
    @GetMapping("/name/{name}")
    public List<CategoryDTO> getCategoryByName(@PathVariable String name){
        return categoryService.getByName(name);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id){
        categoryService.delete(id);
    }
}