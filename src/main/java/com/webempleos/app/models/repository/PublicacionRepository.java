package com.webempleos.app.models.repository;

import com.webempleos.app.models.entity.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicacionRepository extends JpaRepository<Publicacion,Integer> {
}
