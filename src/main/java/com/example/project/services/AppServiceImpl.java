package com.example.project.services;

import com.example.project.errors.ServiceException;
import com.example.project.models.App;
import com.example.project.repositories.AppRepository;
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
public class AppServiceImpl implements IAppService {

    @Autowired
    private AppRepository repository;

    @Autowired
    ICategoryService categoryService;

    /*
     * Retorna uma lista com todos os objetos do banco de dados
     * */
    @Override
    public List<App> findAll() {
        return repository.findAll();
    }

    /*
     * Retorna um Optional do Java.util contendo um único
     * item igual o id passado na assinatura do método
     * @param id um id que queira pesquisar no banco
     * */
    @Override
    public Optional<App> findById(Long id) {
        return repository.findById(id);
    }

    /*
     * Retorna o objeto que foi salvo no banco de dados
     * @param um instância de App sem id preenchido
     * */
    @Override
    public App save(App obj) {
        try {
            obj.setId(null);
            obj.setCategory(
                    categoryService.findById(obj.getCategory().getId())
                            .orElseThrow(() -> new ServiceException("Categoria não encontrada", HttpStatus.NOT_FOUND))
            );
            return repository.save(obj);
        } catch (Exception ex) {
            throw new ServiceException("Erro ao salvar registro", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * Retorna o objeto que foi salvo no banco de dados
     * @param um instância de App com id preenchido
     * */
    @Override
    public App update(App obj) {
        try {
            App appFound = repository.findById(obj.getId()).orElseThrow(() -> new ServiceException("Erro ao procurar registro", HttpStatus.NOT_FOUND));
            BeanUtils.copyProperties(obj, appFound, getNullPropertyNames(obj));
            return repository.save(obj);
        } catch (Exception ex) {
            throw new ServiceException("Erro ao atualizar objeto", HttpStatus.INTERNAL_SERVER_ERROR);
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
            throw new ServiceException("Erro ao deletar objeto", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * Retorna uma lista com base nos dois parâmetros passados para o repositório
     * @param nome para ser comparado
     * @param id da categoria a ser buscado
     * */
    @Override
    public List<App> findByFilter(String nome, Long tipo) {
        return repository.findByFilter(nome, tipo);
    }

    /*
     * Retorna uma lista com base no parâmetro passado
     * @param id de uma categoria
     * */
    @Override
    public List<App> findByTipoOrderPrice(Long tipoId) {
        return repository.findByTipoOrderPrice(tipoId);
    }

    /*
     * Método para procurar campos nulos dentro do parâmetro passado
     * @param instância de um App
     * */
    private String[] getNullPropertyNames(App obj) {
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
