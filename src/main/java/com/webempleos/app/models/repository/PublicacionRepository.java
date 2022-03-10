package com.webempleos.app.models.repository;

import com.webempleos.app.models.entity.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PublicacionRepository extends JpaRepository<Publicacion, Integer> {

    List<Publicacion> findAllByCategoriaNombre(String nombreCategoria);

    List<Publicacion> findAllByTituloLikeOrDescripcionLike(String titulo, String descripcion);

}
