package com.mobydigital.votaciones.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para recibir datos al crear o actualizar un partido politico
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartidoPoliticoRequestDTO {

    @NotBlank(message = "El nombre no puede estar vacio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "La sigla no puede estar vacia")
    @Size(min = 2, max = 20, message = "La sigla debe tener entre 2 y 20 caracteres")
    @Pattern(regexp = "^[A-Z]+$", message = "La sigla solo puede tener letras mayusculas")
    private String sigla;

}
