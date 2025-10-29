package com.mobydigital.votaciones.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa un partido político.
 *
 * Un partido político puede tener múltiples candidatos asociados.
 */
@Entity
@Table(name = "partidos_politicos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartidoPolitico {

    /**
     * Identificador único del partido político.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre completo del partido político.
     */
    @NotBlank(message = "El nombre del partido político no puede estar vacío")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nombre;

    /**
     * Sigla o acrónimo del partido político.
     * Debe contener solo letras mayúsculas y tener entre 2 y 20 caracteres.
     */
    @NotBlank(message = "La sigla del partido político no puede estar vacía")
    @Size(min = 2, max = 20, message = "La sigla debe tener entre 2 y 20 caracteres")
    @Pattern(regexp = "^[A-Z]+$", message = "La sigla debe contener solo letras mayúsculas")
    @Column(nullable = false, unique = true, length = 20)
    private String sigla;

}
