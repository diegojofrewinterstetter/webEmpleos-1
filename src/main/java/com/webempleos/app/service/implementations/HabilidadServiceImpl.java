
package com.webempleos.app.service.implementations;

import com.webempleos.app.models.entity.Habilidad;
import com.webempleos.app.models.repository.HabilidadRepository;
import com.webempleos.app.service.interfaces.HabilidadService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HabilidadServiceImpl implements HabilidadService{
    
    @Autowired
    private HabilidadRepository habilidadRepository;
    
    @Override
    public List<Habilidad> findAll() {
        return habilidadRepository.findAll();
    }

    @Override
    public Optional<Habilidad> findById(Integer id) {
        return habilidadRepository.findById(id);
    }

    @Override
    public void save(Habilidad habilidad) {
        habilidadRepository.save(habilidad);
    }

    @Override
    public void deleteById(Integer id) {
        habilidadRepository.deleteById(id);
    }
    
}
