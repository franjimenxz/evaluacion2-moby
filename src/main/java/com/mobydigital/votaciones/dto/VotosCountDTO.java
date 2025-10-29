package com.mobydigital.votaciones.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para devolver el conteo de votos
 * Puede ser usado para candidatos o partidos
 */
@Schema(description = "Estadisticas de votos para candidato o partido")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VotosCountDTO {

    @Schema(description = "Identificador del candidato o partido", example = "1")
    private Long id;

    @Schema(description = "Nombre del candidato o partido", example = "Juan Perez")
    private String nombre;

    @Schema(description = "Cantidad total de votos", example = "150")
    private Long cantidadVotos;

}
