package com.webempleos.app.service.implementations;

import com.webempleos.app.models.entity.Publicacion;
import com.webempleos.app.models.repository.PublicacionRepository;
import com.webempleos.app.service.interfaces.PublicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublicacionServiceImpl implements PublicacionService {

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Override
    public List<Publicacion> findAll() {
        return publicacionRepository.findAll();
    }

    @Override
    public List<Publicacion> findAllByCategoriaNombre(String nombreCategoria) {
        return publicacionRepository.findAllByCategoriaNombre(nombreCategoria);
    }

    @Override
    public Page<Publicacion> findAll(Pageable pageable) {
        return publicacionRepository.findAll(pageable);
    }

    @Override
    public List<Publicacion> findAllByTituloLikeOrDescripcionLike(String titulo, String descripcion) {
        return publicacionRepository.findAllByTituloLikeOrDescripcionLike(titulo,descripcion);
    }

    @Override
    public Optional<Publicacion> findById(Integer id) {
        return publicacionRepository.findById(id);
    }

    @Override
    public void save(Publicacion publicacion) {
        publicacionRepository.save(publicacion);
    }

    @Override
    public void deleteById(Integer id) {
        publicacionRepository.deleteById(id);
    }
}
