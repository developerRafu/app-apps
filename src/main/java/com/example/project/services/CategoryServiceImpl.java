package com.example.project.services;

import com.example.project.errors.ApiException;
import com.example.project.errors.ServiceException;
import com.example.project.models.Category;
import com.example.project.repositories.CategoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryRepository repository;

    /*
     * Retorna uma lista com todos os objetos do banco de dados
     * */
    @Override
    public List<Category> findAll() {
        return repository.findAll();
    }
    /*
     * Retorna um Optional do Java.util contendo um único
     * item igual o id passado na assinatura do método
     * @param id um id que queira pesquisar no banco
     * */
    @Override
    public Optional<Category> findById(Long id) {
        return repository.findById(id);
    }
    /*
     * Retorna o objeto que foi salvo no banco de dados
     * @param um instância de Category sem id preenchido
     * */
    @Override
    public Category save(Category obj) {
        try {
            return repository.save(obj);
        }catch (Exception ex){
            throw new ServiceException("Erro ao salvar", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /*
     * Retorna o objeto que foi salvo no banco de dados
     * @param um instância de Category com id preenchido
     * */
    @Override
    public Category update(Category obj) {
        try {
            Category CategoryFound = this.findById(obj.getId()).orElseThrow(() -> new ApiException("Erro ao procurar registro", HttpStatus.NOT_FOUND));
            BeanUtils.copyProperties(CategoryFound, obj, getNullPropertyNames(obj));
            return repository.save(obj);
        }catch (Exception ex){
            throw new ServiceException("Erro ao atualizar registro", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /*
     * Não retorna nada, porém faz a mediação entre quem chama
     * o método e o repositório
     * @param um id existente no banco de dados
     * */
    @Override
    public void deleteById(Long id) {
        try {
            this.repository.deleteById(id);
        }catch (Exception ex){
            throw new ServiceException("Erro ao salvar registro", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /*
     * Método para procurar campos nulos dentro do parâmetro passado
     * @param instância de um App
     * */
    private String[] getNullPropertyNames(Category obj) {
        final BeanWrapper src = new BeanWrapperImpl(obj);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
