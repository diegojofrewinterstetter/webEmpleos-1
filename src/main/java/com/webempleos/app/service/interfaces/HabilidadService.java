
package com.webempleos.app.service.interfaces;

import com.webempleos.app.models.entity.Habilidad;
import java.util.List;
import java.util.Optional;


public interface HabilidadService {
    
    List<Habilidad> findAll();
    
    Optional<Habilidad> findById(Integer id);
    
    void save(Habilidad habilidad);
    
    void deleteById(Integer id);
    
    
}
