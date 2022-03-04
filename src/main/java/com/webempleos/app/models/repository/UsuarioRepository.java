package com.webempleos.app.models.repository;

import com.webempleos.app.models.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByNombre(String nombre);
    Optional<Usuario> findByUsername(String username);

}
