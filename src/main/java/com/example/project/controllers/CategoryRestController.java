package com.example.project.controllers;

import com.example.project.models.Category;
import com.example.project.models.dtos.CategoryDto;
import com.example.project.services.ICategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryRestController {

    @Autowired
    private ICategoryService service;
    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAll() {
        return ResponseEntity.ok().body(
                this.service.findAll().stream().map(this::convertToDto).collect(Collectors.toList())
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<CategoryDto> getById(@PathVariable Long id) {
        return this.service.findById(id)
                .map(obj -> ResponseEntity.ok(this.convertToDto(obj)))
                .orElseThrow(() -> new RuntimeException("Erro ao buscar registro"));
    }

    @PostMapping
    public ResponseEntity<CategoryDto> save(@RequestBody CategoryDto dto){
        Category obj = this.convertToEntity(dto);
        obj = this.service.save(obj);
        dto = this.convertToDto(obj);
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping
    public ResponseEntity<CategoryDto> update(@RequestBody CategoryDto dto){
        Category obj = this.convertToEntity(dto);
        obj = this.service.update(obj);
        dto = this.convertToDto(obj);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteBydId(@PathVariable Long id){
        this.service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private Category convertToEntity(CategoryDto dto) {
        return this.mapper.map(dto, Category.class);
    }

    private CategoryDto convertToDto(Category obj) {
        return this.mapper.map(obj, CategoryDto.class);
    }
}
