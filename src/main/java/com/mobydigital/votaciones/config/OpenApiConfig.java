package com.mobydigital.votaciones.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuración de OpenAPI/Swagger para la documentación de la API.
 *
 * Esta clase configura la información general de la API que se mostrará
 * en la interfaz de Swagger UI.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI sistemaVotacionesOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sistema de Gestión de Votaciones API")
                        .description("API REST para gestionar un sistema de votaciones a nivel local. " +
                                "Permite registrar partidos políticos, candidatos y votos emitidos por electores.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Moby Digital Academy")
                                .email("academy@mobydigital.com")
                                .url("https://mobydigital.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor de desarrollo local")
                ));
    }
}
