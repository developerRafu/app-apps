package com.example.project.services;

import com.example.project.models.App;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface IAppService {
    List<App> findAll();
    Optional<App> findById(Long id);
    App save(App obj);
    App update(App obj);
    void deleteById(Long id);
    List<App> findByFilter(String nome, Long tipo);
    List<App> findByTipoOrderPrice(Long tipo);
}
