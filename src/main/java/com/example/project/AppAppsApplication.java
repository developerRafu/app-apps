package com.example.project;

import com.example.project.models.App;
import com.example.project.models.Category;
import com.example.project.services.IAppService;
import com.example.project.services.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class AppAppsApplication implements CommandLineRunner {

    @Autowired
    private IAppService appService;
    @Autowired
    private ICategoryService categoryService;

    public static void main(String[] args) {
        SpringApplication.run(AppAppsApplication.class, args);
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        Category category = new Category(null, "Nãosei");
        category = categoryService.save(category);
        App a1 = new App(null, "jojo", "Não sei qual funcionalidade", 10.0, category);
        a1 = appService.save(a1);
        category.getApps().add(a1);
    }
}
