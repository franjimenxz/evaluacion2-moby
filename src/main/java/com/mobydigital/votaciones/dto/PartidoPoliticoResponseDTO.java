package com.mobydigital.votaciones.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para devolver informacion de un partido politico
 */
@Schema(description = "Respuesta con datos de un partido politico")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartidoPoliticoResponseDTO {

    @Schema(description = "Identificador unico del partido", example = "1")
    private Long id;

    @Schema(description = "Nombre completo del partido politico", example = "Union Civica Radical")
    private String nombre;

    @Schema(description = "Sigla del partido", example = "UCR")
    private String sigla;

}
