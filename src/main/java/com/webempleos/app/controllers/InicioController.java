package com.webempleos.app.controllers;

import com.webempleos.app.models.entity.Usuario;
import com.webempleos.app.service.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = {"/", ""})
public class InicioController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping(value = {"/", ""})
    public String inicio() {
        return "index";
    }

    @GetMapping(value = "/ingresar")
    public String ingreso(Authentication authentication, HttpSession session) {
        String username = authentication.getName();

        if(session.getAttribute("usuario")==null){
            Usuario usuario=usuarioService.findByUsername(username).orElse(null);
            usuario.setPassword(null);
            session.setAttribute("usuario",usuario);
        }
        return "redirect:/usuarios/listar";
    }

    @GetMapping(value = "/login")
    public String login(){
        return "login";
    }

    @GetMapping(value = "/logout")
    public String logout(HttpServletRequest request){
        SecurityContextLogoutHandler logoutHandler=new SecurityContextLogoutHandler();
        logoutHandler.logout(request,null,null);
        return "redirect:/login";
    }
}
