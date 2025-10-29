package com.mobydigital.votaciones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO para devolver informacion de un voto con datos del candidato
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VotoResponseDTO {

    private Long id;
    private CandidatoResponseDTO candidato;
    private LocalDateTime fechaEmision;

}
