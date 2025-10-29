package com.mobydigital.votaciones.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para registrar un voto
 * Solo necesita el ID del candidato, la fecha se asigna automaticamente
 */
@Schema(description = "Datos para registrar un nuevo voto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VotoRequestDTO {

    @Schema(description = "ID del candidato al que se vota", example = "1")
    @NotNull(message = "Debe especificar el ID del candidato")
    private Long candidatoId;

}
