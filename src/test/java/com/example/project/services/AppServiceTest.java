package com.example.project.services;

import com.example.project.models.App;
import com.example.project.models.Category;
import com.example.project.repositories.AppRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@RunWith(MockitoJUnitRunner.class)
public class AppServiceTest {

    @InjectMocks
    AppServiceImpl service;

    @Mock
    AppRepository repository;

    @Mock
    CategoryServiceImpl categoryService;

    @Test
    public void it_should_save_an_App(){
        App app = new App();
        app.setId(1L);
        app.setNome("Teste app");
        app.setDescricao("App para testes");
        app.setPreco(1000.0);

        Category category = new Category(1L, "Categoria de testes");
        category.getApps().add(app);

        app.setCategory(category);

        BDDMockito.given(categoryService.findById(1l)).willReturn(Optional.of(category));

        BDDMockito.given(repository.save(app)).willReturn(app);

        App result = service.save(app);

        Assertions.assertThat(result).isNotNull();
    }

    @Test
    public void it_should_break_if_there_is_no_category(){
        App app = new App();
        app.setId(1L);
        app.setNome("Teste app");
        app.setDescricao("App para testes");
        app.setPreco(1000.0);

        Category category = new Category(1L, "Categoria de testes");
        category.getApps().add(app);

        app.setCategory(category);

        BDDMockito.given(categoryService.findById(1l)).willReturn(Optional.empty());

        assertThatThrownBy(()->service.save(app))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Categoria não encontrada");
    }

    @Test
    public void it_should_update_an_App(){
        App app = new App();
        app.setId(1L);
        app.setNome("Teste app");
        app.setDescricao("App para testes");
        app.setPreco(1000.0);

        Category category = new Category(1L, "Categoria de testes");
        category.getApps().add(app);

        app.setCategory(category);

        App newApp = new App(1L, "Teste novo", "testestets", 1500.0, category);

        BDDMockito.given(repository.findById(1L)).willReturn(Optional.of(app));

        BDDMockito.given(repository.save(newApp)).willReturn(newApp);

        App result = service.update(newApp);

        assertThat(result.getPreco()).isEqualTo(1500.0);
    }
}
