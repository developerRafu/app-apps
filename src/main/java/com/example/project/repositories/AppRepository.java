package com.example.project.repositories;

import com.example.project.models.App;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppRepository extends JpaRepository<App, Long> {

    @Query("SELECT a FROM App a " +
            "WHERE 1=1 " +
            "AND (:nome IS NULL OR LOWER(a.nome) LIKE LOWER(CONCAT('%',:nome,'%') ) ) " +
            "AND (:tipoId IS NULL OR a.category.id = :tipoId)")
    List<App> findByFilter(@Param("nome") String nome,@Param("tipoId") Long tipo);

    @Query("SELECT a FROM App a " +
            "WHERE (:tipoId IS NULL OR a.category.id = :tipoId) " +
            "ORDER BY a.preco ASC")
    List<App> findByTipoOrderPrice(@Param("tipoId") Long tipoId);
}
