package com.webempleos.app.service.interfaces;

import com.webempleos.app.models.entity.Publicacion;

import java.util.List;
import java.util.Optional;

public interface PublicacionService {

    List<Publicacion> findAll();

    List<Publicacion> findAllByCategoriaNombre(String nombreCategoria);

    Optional<Publicacion> findById(Integer id);

    void save(Publicacion publicacion);

    void deleteById(Integer id);
}
