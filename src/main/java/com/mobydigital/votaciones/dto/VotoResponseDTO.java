package com.mobydigital.votaciones.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO para devolver informacion de un voto con datos del candidato
 */
@Schema(description = "Respuesta con datos de un voto registrado")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VotoResponseDTO {

    @Schema(description = "Identificador unico del voto", example = "1")
    private Long id;

    @Schema(description = "Datos del candidato votado")
    private CandidatoResponseDTO candidato;

    @Schema(description = "Fecha y hora de emision del voto", example = "2024-01-15T10:30:00")
    private LocalDateTime fechaEmision;

}
