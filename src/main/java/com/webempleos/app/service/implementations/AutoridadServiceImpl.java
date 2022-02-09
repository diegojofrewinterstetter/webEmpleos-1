
package com.webempleos.app.service.implementations;

import com.webempleos.app.models.entity.Autoridad;
import com.webempleos.app.models.repository.AutoridadRepository;
import com.webempleos.app.service.interfaces.AutoridadService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutoridadServiceImpl implements AutoridadService{

    @Autowired
    private AutoridadRepository autoridadRepository;
    
    @Override
    public List<Autoridad> findAll() {
        return autoridadRepository.findAll();
    }

    @Override
    public Optional<Autoridad> findById(Integer id) {
        return autoridadRepository.findById(id);
    }

    @Override
    public void save(Autoridad autoridad) {
        autoridadRepository.save(autoridad);
    }

    @Override
    public void deleteById(Integer id) {
        autoridadRepository.deleteById(id);
    }
    
}
