package com.mobydigital.votaciones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para devolver informacion de un candidato con su partido
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidatoResponseDTO {

    private Long id;
    private String nombreCompleto;
    private PartidoPoliticoResponseDTO partido;

}
