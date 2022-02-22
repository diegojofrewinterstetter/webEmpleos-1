
package com.webempleos.app.service.implementations;

import com.webempleos.app.models.entity.Categoria;
import com.webempleos.app.models.repository.CategoriaRepository;
import com.webempleos.app.service.interfaces.CategoriaService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaServiceImpl implements CategoriaService {
    
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
        }

    @Override
    public Optional<Categoria> findById(Integer id) {
        return categoriaRepository.findById(id);
        }

    @Override
    public Optional<Categoria> findByNombre(String nombre) {
        return categoriaRepository.findByNombre(nombre);
    }

    @Override
    public void deleteById(Integer id) {
        categoriaRepository.deleteById(id);
        }

    @Override
    public void save(Categoria categoria) {
        categoriaRepository.save(categoria);
        }
    
    
}
