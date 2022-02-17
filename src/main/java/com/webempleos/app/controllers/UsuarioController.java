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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping(value = "/usuarios")
@SessionAttributes(value = "usuario")
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
    public String crear(@Valid Usuario usuario,
                        BindingResult result, @RequestParam(value = "foto", required = false) MultipartFile foto, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "form-usuario";
        }

        byte[] contenido = null;
//        Verificamos que el archivo no este vacio
        if (!foto.isEmpty()) {
            //Verficiamos que el contenido del archivo sea una foto tipo jpg o png
            if (foto.getContentType().endsWith("jpeg") || foto.getContentType().endsWith("png")) {
                try {
                    contenido = foto.getBytes();
                    usuario.setImagen(contenido);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Hola el error es : " + e.getMessage());
                }
            }
        }
        usuarioService.save(usuario);
        redirectAttributes.addFlashAttribute("success", "El usuario ha sido creado con exito");
        return "redirect:/usuarios/listar";
    }

    @GetMapping(value = "/editar/{id}")
    public String editar(@PathVariable(value = "id") Integer id, Model model) {
        model.addAttribute("titulo", "Datos del usuario");
        model.addAttribute("usuario", usuarioService.findById(id));
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
