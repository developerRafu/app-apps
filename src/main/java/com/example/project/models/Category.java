package com.example.project.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TAB_CATEGORY")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @JsonIgnore
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<App> apps = new ArrayList<>();

    public Category(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }
}
