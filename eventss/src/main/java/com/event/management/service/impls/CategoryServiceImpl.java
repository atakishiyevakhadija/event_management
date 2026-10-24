package com.event.management.service.impls;

import com.event.management.model.dto.CategoryDTO;
import com.event.management.model.dto.CreateCategoryRequest;
import com.event.management.model.dto.UpdateCategoryRequest;
import com.event.management.model.entity.Category;
import com.event.management.model.mapper.CategoryMapper;
import com.event.management.repository.CategoryRepository;
import com.event.management.service.interfaces.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDTO create(CreateCategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());

        Category saved = categoryRepository.save(category);

        return categoryMapper.mapToDto(saved);
    }

    @Override
    public CategoryDTO update(Long id, UpdateCategoryRequest updateRequest) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found!"));

        category.setName(updateRequest.getName());
        category.setDescription(updateRequest.getDescription());

        Category updated = categoryRepository.save(category);

        return categoryMapper.mapToDto(updated);
    }

    @Override
    public Page<CategoryDTO> getAll(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Category> categories = categoryRepository.findAll(pageable);

        return categories.map(categoryMapper::mapToDto);
    }

    @Override
    public CategoryDTO getById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found!"));

        return categoryMapper.mapToDto(category);
    }

    @Override
    public List<CategoryDTO> getByName(String name) {
        List<Category> category = categoryRepository.findByNameContainingIgnoreCase(name);

        return category.stream()
                .map(categoryMapper::mapToDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found!"));
        categoryRepository.delete(category);
    }
}