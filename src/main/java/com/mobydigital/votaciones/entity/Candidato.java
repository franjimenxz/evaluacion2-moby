package com.mobydigital.votaciones.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa un candidato en el sistema de votaciones.
 *
 * Un candidato debe estar asociado a un partido político.
 */
@Entity
@Table(name = "candidatos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Candidato {

    /**
     * Identificador único del candidato.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre completo del candidato.
     */
    @Column(nullable = false, length = 150)
    private String nombreCompleto;

}
