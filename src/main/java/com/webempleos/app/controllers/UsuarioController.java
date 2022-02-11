package com.webempleos.app.controllers;

import com.webempleos.app.models.entity.Usuario;
import com.webempleos.app.service.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping(value = "/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping(value = "/listar")
    public String listar(Model model) {
        model.addAttribute("titulo", "Listado de usuarios");
        model.addAttribute("usuarios", usuarioService.findAll());
        return "listar-usuarios";
    }

    @GetMapping(value = "/crear")
    public String crear(Model model) {
        model.addAttribute("titulo", "Formulario del usuario");
        model.addAttribute("usuario", new Usuario());
        return "form-usuario";
    }

    @PostMapping(value = "/crear")
    public String crear(Usuario usuario, RedirectAttributes redirectAttributes, @RequestParam(name = "imagen", required = false) MultipartFile imagen) {
        byte[] contenido = null;

        //Verificamos que el archivo no este vacio
//        if (!imagen.isEmpty()) {
//            //Verficiamos que el contenido del archivo sea una foto tipo jpg o png
//            if (imagen.getContentType().endsWith("jpeg") || imagen.getContentType().endsWith("png")) {
//                try {
//                    contenido = imagen.getBytes();
//                    usuario.setImagen(contenido);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        usuarioService.save(usuario);
        redirectAttributes.addFlashAttribute("success", "El usuario ha sido creado con exito");
        return "redirect:/usuarios/listar";
    }

    @GetMapping(value = "/editar")
    public String editar(Model model) {
        model.addAttribute("titulo", "Datos del usuario");
        model.addAttribute("usuario", new Usuario());
        return "editar-usuario";
    }

    @GetMapping(value = "/eliminar/{id}")
    public String eliminar(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {
        usuarioService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "El usuario ha sido eliminado con exito");
        return "redirect:/usuarios/listar";
    }

    @GetMapping(value = "/imagen/{id}")
    public ResponseEntity<byte[]> fotoUsuario(@PathVariable(value = "id") Integer id) {
        Usuario usuario = usuarioService.findById(id).get();

        if (usuario.getImagen() != null) {

            HttpHeaders cabecera = new HttpHeaders();
            cabecera.setContentType(MediaType.IMAGE_JPEG);

            return new ResponseEntity<>(usuario.getImagen(), cabecera, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
