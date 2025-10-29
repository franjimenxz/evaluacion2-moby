package com.mobydigital.votaciones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para devolver el conteo de votos
 * Puede ser usado para candidatos o partidos
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VotosCountDTO {

    private Long id;
    private String nombre;
    private Long cantidadVotos;

}
