package com.webempleos.app.service.interfaces;

import com.webempleos.app.models.entity.Usuario;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    List<Usuario> findAll();

    Optional<Usuario> findByNombre(String nombre);

    Optional<Usuario> findById(Integer id);

    Optional<Usuario> findByUsername(String username);

    void save(Usuario usuario);

    void deleteById(Integer id);

}
