package com.example.project.controllers;

import com.example.project.models.App;
import com.example.project.models.dtos.AppDto;
import com.example.project.services.IAppService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/apps")
public class AppRestController {

    @Autowired
    private IAppService service;
    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<AppDto>> getAll() {
        return ResponseEntity.ok().body(
                this.service.findAll().stream().map(this::convertToDto).collect(Collectors.toList())
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<AppDto> getById(@PathVariable Long id) {
        return this.service.findById(id)
                .map(obj -> ResponseEntity.ok(this.convertToDto(obj)))
                .orElseThrow(() -> new RuntimeException("Erro ao buscar registro"));
    }

    @GetMapping("search")
    public ResponseEntity<List<AppDto>> findByFilter(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "tipo", required = false) Long tipo
    ){
        return ResponseEntity.ok().body(
                this.service.findByFilter(nome, tipo).stream()
                        .map(this::convertToDto).collect(Collectors.toList())
        );
    }

    @GetMapping("search/price")
    public ResponseEntity<List<AppDto>> findByTipoOrderPrice(
            @RequestParam(value = "tipo", required = false) Long tipo
    ){
        return ResponseEntity.ok().body(
                this.service.findByTipoOrderPrice(tipo).stream()
                        .map(this::convertToDto).collect(Collectors.toList())
        );
    }

    @PostMapping
    public ResponseEntity<AppDto> save(@RequestBody AppDto dto){
        App obj = this.convertToEntity(dto);
        obj = this.service.save(obj);
        dto = this.convertToDto(obj);
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping
    public ResponseEntity<AppDto> update(@RequestBody AppDto dto){
        App obj = this.convertToEntity(dto);
        obj = this.service.update(obj);
        dto = this.convertToDto(obj);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteBydId(@PathVariable Long id){
        this.service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private App convertToEntity(AppDto dto) {
        return this.mapper.map(dto, App.class);
    }

    private AppDto convertToDto(App app) {
        return this.mapper.map(app, AppDto.class);
    }

}
