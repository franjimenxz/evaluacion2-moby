package com.mobydigital.votaciones.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para recibir datos al crear o actualizar un candidato
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidatoRequestDTO {

    @NotBlank(message = "El nombre completo no puede estar vacio")
    @Size(min = 3, max = 150, message = "El nombre debe tener entre 3 y 150 caracteres")
    private String nombreCompleto;

    @NotNull(message = "Debe especificar el ID del partido politico")
    private Long partidoId;

}
