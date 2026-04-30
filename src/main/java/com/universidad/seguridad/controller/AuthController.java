package com.universidad.seguridad.controller;

import com.universidad.seguridad.model.Usuario;
import com.universidad.seguridad.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controlador para autenticación, registro, dashboard y panel admin.
 * Maneja las rutas: /login, /registro, /dashboard, /admin
 */
@Controller
public class AuthController {

    private final UsuarioService service;

    public AuthController(UsuarioService service) {
        this.service = service;
    }

    /**
     * Muestra el formulario de login personalizado.
     */
    @GetMapping("/login")
    public String mostrarLogin() {
        return "auth/login";
    }

    /**
     * Muestra el formulario de registro de nuevo usuario.
     */
    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "auth/registro";
    }

    /**
     * Procesa el registro de un nuevo usuario.
     * Valida los campos, hashea la contraseña con BCrypt y guarda en MySQL.
     * Si el email ya existe, muestra error en el formulario.
     */
    @PostMapping("/registro")
    public String registrar(@Valid @ModelAttribute Usuario usuario,
                            BindingResult result) {
        if (result.hasErrors()) return "auth/registro";
        try {
            service.registrar(usuario);
            return "redirect:/login?registrado";
        } catch (RuntimeException e) {
            result.rejectValue("email", "error.email", e.getMessage());
            return "auth/registro";
        }
    }

    /**
     * Dashboard principal. Accesible para usuarios autenticados.
     * Muestra el nombre del usuario y sus roles.
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication auth) {
        model.addAttribute("usuario", auth.getName());
        model.addAttribute("roles", auth.getAuthorities());
        return "dashboard";
    }

    /**
     * Panel de administración. Solo accesible para ROLE_ADMIN.
     * Muestra la lista completa de usuarios registrados.
     */
    @GetMapping("/admin")
    public String adminPanel(Model model) {
        model.addAttribute("usuarios", service.listarTodos());
        return "admin/panel";
    }
}
