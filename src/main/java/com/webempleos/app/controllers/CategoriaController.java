package com.webempleos.app.controllers;

import com.webempleos.app.models.entity.Categoria;
import com.webempleos.app.models.entity.Publicacion;
import com.webempleos.app.service.interfaces.CategoriaService;
import com.webempleos.app.service.interfaces.PublicacionService;
import com.webempleos.app.specification.PublicacionSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.List;

@Controller
@RequestMapping(value = "/categorias")
@SessionAttributes(value = "nombreCategoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;
    @Autowired
    private PublicacionService publicacionService;

//    @GetMapping(value = "/listar/{nombreCategoria}")
//    public String porCategoria(@PathVariable(value = "nombreCategoria") String nombreCategoria, Model model) {
//        List<Publicacion> publicaciones = publicacionService.findAllByCategoriaNombre(nombreCategoria);
//        publicaciones.forEach(publicacion -> System.out.println(publicacion.getTitulo()));
//        model.addAttribute("titulo", "Listado de publicaciones");
//        if(publicaciones.isEmpty()){
//            return "redirect:/publicaciones/listar";
//        }
//        model.addAttribute("publicaciones", publicaciones);
//        return "listar-publicacion";
//    }

    @GetMapping(value = "/listar/{nombreCategoria}")
    public String porCategoria(@PathVariable(value = "nombreCategoria") String nombreCategoria, Model model) {
        Publicacion publicacion = new Publicacion();
        Categoria categoria = categoriaService.findByNombre(nombreCategoria).orElse(null);
        publicacion.setCategoria(categoria);

        /*Lo añadimos a la session*/
        model.addAttribute("nombreCategoria", nombreCategoria);

        Specification<Publicacion> specification = PublicacionSpecification.publicacionSpecification(publicacion);
        model.addAttribute("publicaciones", publicacionService.findAll(specification, PageRequest.of(0, 6)));
        model.addAttribute("titulo", "Listado de publicaciones");
        return "listar-publicacion-categoria";
    }

    @GetMapping(value = "/listar")
    public String porCategorias(Pageable pageable, @SessionAttribute(value = "nombreCategoria") String nombreCategoria, Model model) {
        Publicacion publicacion = new Publicacion();
        Categoria categoria = categoriaService.findByNombre(nombreCategoria).orElse(null);
        publicacion.setCategoria(categoria);
        Specification<Publicacion> specification = PublicacionSpecification.publicacionSpecification(publicacion);
        model.addAttribute("publicaciones", publicacionService.findAll(specification, PageRequest.of(pageable.getPageNumber(), 6)));

        model.addAttribute("titulo", "Listado de publicaciones");
        return "listar-publicacion-categoria";
    }

}
