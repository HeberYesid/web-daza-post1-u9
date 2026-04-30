package com.universidad.seguridad.service;

import com.universidad.seguridad.model.Usuario;
import com.universidad.seguridad.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio de negocio para operaciones de Usuario.
 * Gestiona el registro con hasheo BCrypt y consultas.
 */
@Service
public class UsuarioService {

    private final UsuarioRepository repo;
    private final PasswordEncoder encoder;

    // Inyección por constructor (buena práctica — sin @Autowired)
    public UsuarioService(UsuarioRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    /**
     * Registra un nuevo usuario.
     * - Verifica que el email no exista.
     * - Hashea la contraseña con BCrypt antes de guardar.
     * - Asigna rol ROLE_USER por defecto.
     */
    @Transactional
    public void registrar(Usuario usuario) {
        if (repo.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("El correo ya está registrado");
        }
        // Hashear la contraseña antes de guardar
        usuario.setContrasenia(encoder.encode(usuario.getContrasenia()));
        usuario.setRol("ROLE_USER"); // rol por defecto
        repo.save(usuario);
    }

    /**
     * Lista todos los usuarios registrados.
     * Utilizado por el panel de administración.
     */
    public List<Usuario> listarTodos() {
        return repo.findAll();
    }
}
