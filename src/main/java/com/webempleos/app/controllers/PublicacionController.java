
package com.webempleos.app.controllers;

import com.webempleos.app.models.entity.Categoria;
import com.webempleos.app.models.entity.Publicacion;
import com.webempleos.app.models.entity.Usuario;
import com.webempleos.app.service.interfaces.CategoriaService;
import com.webempleos.app.service.interfaces.PublicacionService;

import java.io.IOException;

import com.webempleos.app.service.interfaces.UsuarioService;
import com.webempleos.app.specification.PublicacionSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/publicaciones")
@SessionAttributes(value = {"publicacion", "valorBusqueda"})
public class PublicacionController {

    @Autowired
    private PublicacionService publicacionService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private CategoriaService categoriaService;

//    @GetMapping("/listar")
//    public String listar(Model model) {
//        model.addAttribute("titulo", "Listado de publicaciones");
//        model.addAttribute("publicaciones", publicacionService.findAll());
//        return "listar-publicacion";
//    }

    @GetMapping(value = "/listar")
    public String listar(Pageable page, Model model) {
        model.addAttribute("titulo", "Listado de publicaciones");
        Page<Publicacion> paginasPublicaciones = publicacionService.findAll(PageRequest.of(page.getPageNumber(), 6));
//        Page<Publicacion> paginasPublicaciones = publicacionService.findAll(PageRequest.of(numPag, tamPag), Sort.by("titulo"));
        if (!paginasPublicaciones.hasContent()) {
            paginasPublicaciones = publicacionService.findAll(PageRequest.of(0, 6));
            model.addAttribute("publicaciones", paginasPublicaciones);
            return "listar-publicacion";
        }
        model.addAttribute("publicaciones", paginasPublicaciones);
        return "listar-publicacion";
    }

    @PostMapping(value = "/buscar")
    public String buscar(@RequestParam(name = "valorBusqueda") String valorBusqueda, Model model) {
        Publicacion publicacion = new Publicacion();
        publicacion.setTitulo(valorBusqueda);
        publicacion.setDescripcion(valorBusqueda);
        publicacion.setCategoria(new Categoria(valorBusqueda));
        model.addAttribute("titulo", "Listado de publicaciones");

        Specification<Publicacion> especificacionBusqueda = PublicacionSpecification.publicacionSpecification(publicacion);
        Page<Publicacion> paginasPublicaciones = publicacionService.findAll(especificacionBusqueda, PageRequest.of(0, 6));

        if (!paginasPublicaciones.hasContent()) {
            return "redirect:/publicaciones/listar";
        }
        //Lo agregamos a la sesion
        model.addAttribute("valorBusqueda",valorBusqueda);
        model.addAttribute("publicaciones", paginasPublicaciones);
        return "listar-publicacion-busqueda";
    }

    @GetMapping(value = "/buscar")
    public String buscar(Pageable pageable, @SessionAttribute(value = "valorBusqueda") String valorBusqueda, Model model) {
        Publicacion publicacion = new Publicacion();
        publicacion.setTitulo(valorBusqueda);
        publicacion.setDescripcion(valorBusqueda);
        model.addAttribute("titulo", "Listado de publicaciones");

        Specification<Publicacion> especificacionBusqueda = PublicacionSpecification.publicacionSpecification(publicacion);
        Page<Publicacion> paginasPublicaciones = publicacionService.findAll(especificacionBusqueda, PageRequest.of(pageable.getPageNumber(), 6));

        if (!paginasPublicaciones.hasContent()) {
            return "redirect:/publicaciones/listar";
        }

        model.addAttribute("publicaciones", paginasPublicaciones);
        return "listar-publicacion-busqueda";
    }


    @GetMapping("/crear")
    public String crear(Model model) {
        model.addAttribute("titulo", "Formulario de la publicacion");
        model.addAttribute("publicacion", new Publicacion());
        model.addAttribute("categorias", categoriaService.findAll());
        return "form-publicacion";
    }

    @PostMapping("/crear/{nombreUser}")
    public String crear(@PathVariable(value = "nombreUser") String nombreUser, @Valid Publicacion publicacion
            , BindingResult result, @RequestParam(name = "foto", required = false) MultipartFile foto
            , RedirectAttributes redirectAttributes, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("titulo", "Formulario de la publicacion");
            model.addAttribute("categorias", categoriaService.findAll());
            return "form-publicacion";
        }

        Usuario usuario = usuarioService.findByUsername(nombreUser).orElse(null);
        publicacion.setUsuario(usuario);
        usuario.getPublicaciones().add(publicacion);
        byte[] contenido = null;

        //Verificamos que el archivo no este vacio
        if (!foto.isEmpty()) {
            //Verficiamos que el contenido del archivo sea una foto tipo jpg o png
            if (foto.getContentType().endsWith("jpeg") || foto.getContentType().endsWith("png") || foto.getContentType().endsWith("svg")) {
                try {
                    contenido = foto.getBytes();
                    publicacion.setImagen(contenido);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        publicacionService.save(publicacion);
        redirectAttributes.addFlashAttribute("success", "La publicacion ha sido creada con exito");
        return "redirect:/publicaciones/listar";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable(value = "id") Integer id, Model model) {
        model.addAttribute("titulo", "Datos de la publicacion");
        model.addAttribute("publicacion", publicacionService.findById(id).orElse(null));
        return "editar-publicacion";
    }

    @PostMapping("/editar")
    public String crear(@Valid Publicacion publicacion, BindingResult result,
                        @RequestParam(name = "foto", required = false) MultipartFile foto
            , RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "form-publicacion";
        }
        byte[] contenido = null;

        //Verificamos que el archivo no este vacio
        if (!foto.isEmpty()) {
            //Verficiamos que el contenido del archivo sea una foto tipo jpg o png
            if (foto.getContentType().endsWith("jpeg") || foto.getContentType().endsWith("png") || foto.getContentType().endsWith("svg")) {
                try {
                    contenido = foto.getBytes();
                    publicacion.setImagen(contenido);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        publicacionService.save(publicacion);
        redirectAttributes.addFlashAttribute("success", "La publicacion ha sido editada con exito");
        return "redirect:/publicaciones/listar";
    }

    @GetMapping(value = "/info/{id}")
    public String info(@PathVariable(value = "id") Integer id, Model model) {
        if (id <= 0) {
            return "redirect:/publicaciones/listar";
        }

        Publicacion publicacion = publicacionService.findById(id).orElse(null);
        if (publicacion != null) {
            model.addAttribute("usuario", publicacion.getUsuario());
            model.addAttribute("publicacion", publicacion);
        }
        return "publicacion/info";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {
        publicacionService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "La publicacion ha sido eliminada con exito");
        return "redirect:/publicaciones/listar";
    }

    @GetMapping("/imagen/{id}")
    public ResponseEntity<byte[]> fotoPublicacion(@PathVariable(value = "id") Integer id) {
        Publicacion publicacion = publicacionService.findById(id).get();

        if (publicacion.getImagen() != null) {

            HttpHeaders cabecera = new HttpHeaders();
            cabecera.setContentType(MediaType.IMAGE_JPEG);

            return new ResponseEntity<>(publicacion.getImagen(), cabecera, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
