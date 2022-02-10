
package com.webempleos.app.controllers;

import com.webempleos.app.models.entity.Publicacion;
import com.webempleos.app.service.interfaces.PublicacionService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/publicaciones")
public class PublicacionController {
    
    @Autowired
    private PublicacionService publicacionService;
    
    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("titulo", "Listado de publicaciones");
        model.addAttribute("publicaciones", publicacionService.findAll());
        return "listar-publicaciones";
    }
    
    @GetMapping("/crear")
    public String crear(Model model){
        model.addAttribute("titulo", "Formulario de la publicacion");
        model.addAttribute("publicacion", new Publicacion());
        return "form-publicacion";
    }
    
    @PostMapping("/crear")
    public String crear(Publicacion publicacion, RedirectAttributes redirectAttributes, 
            @RequestParam(name = "imagen", required = false) MultipartFile imagen){
        byte[] contenido = null;
        
        //Verificamos que el archivo no este vacio
        if (!imagen.isEmpty()) {
            //Verficiamos que el contenido del archivo sea una foto tipo jpg o png
            if (imagen.getContentType().endsWith("jpeg") || imagen.getContentType().endsWith("png")) {
                try {
                    contenido = imagen.getBytes();
                    publicacion.setImagen(contenido);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        publicacionService.save(publicacion);
        redirectAttributes.addFlashAttribute("success", "La publicacion ha sido creada con exito");
        return "redirect:/publicacion/listar";
    }
    
    
    @GetMapping("/editar")
    public String editar(Model model) {
        model.addAttribute("titulo", "Datos de la publicacion");
        model.addAttribute("publicacion", new Publicacion());
        return "editar-publicacion";
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {
        publicacionService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "La publicacion ha sido eliminada con exito");
        return "redirect:/publicacion/listar";
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
