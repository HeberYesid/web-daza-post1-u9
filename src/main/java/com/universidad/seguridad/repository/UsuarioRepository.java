package com.universidad.seguridad.repository;

import com.universidad.seguridad.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio JPA para la entidad Usuario.
 * Proporciona operaciones CRUD y consultas personalizadas.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca un usuario por su correo electrónico.
     * Utilizado por UserDetailsService para la autenticación.
     */
    Optional<Usuario> findByEmail(String email);

    /**
     * Verifica si ya existe un usuario con el correo dado.
     * Utilizado en el registro para evitar duplicados.
     */
    boolean existsByEmail(String email);
}
