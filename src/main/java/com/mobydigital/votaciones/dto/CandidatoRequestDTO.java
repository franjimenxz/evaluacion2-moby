package com.mobydigital.votaciones.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para recibir datos al crear o actualizar un candidato
 */
@Schema(description = "Datos para crear un candidato")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidatoRequestDTO {

    @Schema(description = "Nombre completo del candidato", example = "Juan Perez")
    @NotBlank(message = "El nombre completo no puede estar vacio")
    @Size(min = 3, max = 150, message = "El nombre debe tener entre 3 y 150 caracteres")
    private String nombreCompleto;

    @Schema(description = "ID del partido politico al que pertenece", example = "1")
    @NotNull(message = "Debe especificar el ID del partido politico")
    private Long partidoId;

}
