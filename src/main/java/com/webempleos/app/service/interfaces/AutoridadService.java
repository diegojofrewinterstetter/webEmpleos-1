
package com.webempleos.app.service.interfaces;

import com.webempleos.app.models.entity.Autoridad;
import java.util.List;
import java.util.Optional;


public interface AutoridadService {
    
    List<Autoridad> findAll();
    
    Optional<Autoridad> findById(Integer id);
    
    void save (Autoridad autoridad);
    
    void deleteById (Integer id);
    
}
