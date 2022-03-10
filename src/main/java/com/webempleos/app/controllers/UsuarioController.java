package com.webempleos.app.controllers;

import com.webempleos.app.models.entity.Autoridad;
import com.webempleos.app.models.entity.Usuario;
import com.webempleos.app.service.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
@SessionAttributes(value = {"usuario","usuarioEdicion"})
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping(value = "/listar")
    public String listar(Model model) {
        model.addAttribute("titulo", "Listado de usuarios");
        model.addAttribute("usuarios", usuarioService.findAll());

        return "listar-usuarios";
    }

    @GetMapping(value = "/perfil/{username}")
    public String perfil(@PathVariable(value = "username") String username, Model model) {
        Usuario usuario = usuarioService.findByUsername(username).orElse(null);

        if (usuario == null) {
            return "redirect:/usuarios/listar";
        }

        model.addAttribute("usuario", usuario);
        model.addAttribute("cantidad", usuario.getPublicaciones().size());
        model.addAttribute("titulo", "Perfil del usuario");
        return "perfil-usuario";
    }

    @GetMapping(value = "/info/{id}")
    public String info(@PathVariable(value = "id") Integer id, Model model) {
        Usuario usuario = usuarioService.findById(id).orElse(null);

        if (usuario == null) {
            return "redirect:/usuarios/listar";
        }

        model.addAttribute("usuarioInfo", usuario);
        model.addAttribute("cantidad", usuario.getPublicaciones().size());
        model.addAttribute("titulo", "Perfil del usuario");
        return "info-usuario";
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
        if (usuario.getAutoridades().isEmpty()) {
            Autoridad autoridad = new Autoridad(3, "USUARIO");
            usuario.getAutoridades().add(autoridad);
        }
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioService.save(usuario);
        redirectAttributes.addFlashAttribute("success", "El usuario ha sido creado con exito");
        return "redirect:/usuarios/listar";
    }

    @PostMapping(value = "/editar")
    public String editar(@Valid @ModelAttribute(name = "usuarioEdicion") Usuario usuario,
                         BindingResult result, @RequestParam(value = "foto", required = false) MultipartFile foto, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "editar-usuario";
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
        if (usuario.getAutoridades().isEmpty()) {
            Autoridad autoridad = new Autoridad(3, "USUARIO");
            usuario.getAutoridades().add(autoridad);
        }
//        if (passwordEncoder.upgradeEncoding(usuario.getPassword())) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
//        }
        usuarioService.save(usuario);
        redirectAttributes.addFlashAttribute("success", "El usuario ha sido creado con exito");
        return "redirect:/usuarios/listar";
    }

    @GetMapping(value = "/editar/{username}")
    public String editar(@PathVariable(value = "username") String username, Model model) {
        Usuario usuario = usuarioService.findByUsername(username).orElse(null);
        if (usuario == null) {
            return "redirect:/usuarios/listar";
        }
        model.addAttribute("titulo", "Datos del usuario");
        model.addAttribute("usuarioEdicion", usuario);
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

    @GetMapping(value = "/fotoPerfil/{username}")
    public ResponseEntity<byte[]> fotoPerfil(@PathVariable(value = "username") String username) {
        Usuario usuario = usuarioService.findByUsername(username).get();

        if (usuario.getImagen() != null) {

            HttpHeaders cabecera = new HttpHeaders();
            cabecera.setContentType(MediaType.IMAGE_JPEG);

            return new ResponseEntity<>(usuario.getImagen(), cabecera, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
