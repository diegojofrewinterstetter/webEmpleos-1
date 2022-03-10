package com.webempleos.app.service.interfaces;

import com.webempleos.app.models.entity.Publicacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface PublicacionService {

    List<Publicacion> findAll();

    List<Publicacion> findAllByCategoriaNombre(String nombreCategoria);

    Page<Publicacion> findAll(Pageable pageable);

    List<Publicacion> findAllByTituloLikeOrDescripcionLike(String titulo, String descripcion);

    Optional<Publicacion> findById(Integer id);

    void save(Publicacion publicacion);

    void deleteById(Integer id);
}
