package com.mobydigital.votaciones.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para respuesta de conteo de votos
 * Usado para devolver informacion estructurada sobre el conteo de votos
 */
@Schema(description = "Respuesta con conteo de votos para una entidad")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VotoCountResponseDTO {

    @Schema(description = "Identificador de la entidad (candidato o partido)", example = "1")
    private Long id;

    @Schema(description = "Nombre de la entidad", example = "Martin Lousteau")
    private String nombre;

    @Schema(description = "Cantidad total de votos", example = "150")
    private Long cantidadVotos;

}
