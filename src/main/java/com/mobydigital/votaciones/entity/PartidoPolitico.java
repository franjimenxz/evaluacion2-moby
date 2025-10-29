package com.mobydigital.votaciones.entity;

import jakarta.persistence.*;
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
    @Column(nullable = false, length = 100)
    private String nombre;

    /**
     * Sigla o acrónimo del partido político.
     */
    @Column(nullable = false, unique = true, length = 20)
    private String sigla;

}
