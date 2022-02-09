
package com.webempleos.app.models.repository;

import com.webempleos.app.models.entity.Autoridad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutoridadRepository extends JpaRepository<Autoridad, Integer>{
    
    
    
}
