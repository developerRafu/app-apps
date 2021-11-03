package com.example.project.services;

import com.example.project.models.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    List<Category> findAll();
    Optional<Category> findById(Long id);
    Category save(Category obj);
    Category update(Category obj);
    void deleteById(Long id);
}
