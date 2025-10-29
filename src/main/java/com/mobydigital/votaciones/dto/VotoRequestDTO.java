package com.mobydigital.votaciones.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para registrar un voto
 * Solo necesita el ID del candidato, la fecha se asigna automaticamente
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VotoRequestDTO {

    @NotNull(message = "Debe especificar el ID del candidato")
    private Long candidatoId;

}
