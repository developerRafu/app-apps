package com.example.project.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppDto {
    private Long id;
    private String nome;
    private String descricao;
    private Double preco;

    private Long categoryId;
    private String categoryNome;
}
