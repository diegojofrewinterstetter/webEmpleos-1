package com.webempleos.app.service.interfaces;

import com.webempleos.app.models.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    List<Usuario> findAll();

    Optional<Usuario> findById(Integer id);

    void save(Usuario usuario);

    void deleteById(Integer id);

}
