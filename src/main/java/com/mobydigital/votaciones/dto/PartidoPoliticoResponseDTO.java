package com.mobydigital.votaciones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para devolver informacion de un partido politico
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartidoPoliticoResponseDTO {

    private Long id;
    private String nombre;
    private String sigla;

}
