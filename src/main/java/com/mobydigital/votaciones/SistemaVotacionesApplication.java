package com.mobydigital.votaciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación Sistema de Gestión de Votaciones.
 *
 * Esta aplicación REST permite gestionar un sistema de votaciones a nivel local,
 * incluyendo el registro de partidos políticos, candidatos y votos emitidos.
 *
 * @author Moby Digital Academy
 * @version 1.0.0
 */
@SpringBootApplication
public class SistemaVotacionesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SistemaVotacionesApplication.class, args);
    }

}
