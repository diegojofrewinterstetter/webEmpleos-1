
package com.webempleos.app.service.interfaces;

import com.webempleos.app.models.entity.Categoria;
import java.util.List;
import java.util.Optional;


public interface CategoriaService {
    
    List<Categoria> findAll();
    
    Optional<Categoria> findById(Integer id);

    Optional<Categoria> findByNombre(String nombre);

    void deleteById(Integer id);
    
    void save(Categoria categoria);
}
