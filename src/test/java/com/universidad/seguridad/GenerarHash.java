package com.universidad.seguridad;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Clase temporal para generar hash BCrypt.
 * Ejecutar una sola vez para obtener el hash del usuario ADMIN.
 *
 * El hash generado debe usarse en el INSERT de MySQL:
 *   INSERT INTO usuarios (nombre, email, contrasenia, rol, activo)
 *   VALUES ('Administrador', 'admin@universidad.edu', '[hash]', 'ROLE_ADMIN', 1);
 */
@SpringBootTest
class GenerarHash {

    @Autowired
    PasswordEncoder encoder;

    @Test
    void generarHashAdmin() {
        String hash = encoder.encode("admin123");
        System.out.println("==========================================");
        System.out.println("Hash BCrypt para 'admin123':");
        System.out.println(hash);
        System.out.println("==========================================");
        System.out.println("SQL para insertar admin:");
        System.out.println("INSERT INTO usuarios (nombre, email, contrasenia, rol, activo)");
        System.out.println("VALUES ('Administrador', 'admin@universidad.edu',");
        System.out.println("        '" + hash + "', 'ROLE_ADMIN', 1);");
        System.out.println("==========================================");
    }
}
