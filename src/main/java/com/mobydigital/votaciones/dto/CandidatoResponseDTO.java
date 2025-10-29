package com.mobydigital.votaciones.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para devolver informacion de un candidato con su partido
 */
@Schema(description = "Respuesta con datos de un candidato y su partido")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidatoResponseDTO {

    @Schema(description = "Identificador unico del candidato", example = "1")
    private Long id;

    @Schema(description = "Nombre completo del candidato", example = "Juan Perez")
    private String nombreCompleto;

    @Schema(description = "Datos del partido politico al que pertenece")
    private PartidoPoliticoResponseDTO partido;

}
