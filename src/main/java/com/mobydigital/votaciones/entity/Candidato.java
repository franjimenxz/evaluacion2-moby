package com.mobydigital.votaciones.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "El nombre completo del candidato no puede estar vacío")
    @Size(min = 3, max = 150, message = "El nombre completo debe tener entre 3 y 150 caracteres")
    @Column(nullable = false, length = 150)
    private String nombreCompleto;

    /**
     * Partido político al que pertenece el candidato.
     * Relación ManyToOne: Muchos candidatos pueden pertenecer a un mismo partido.
     */
    @NotNull(message = "El candidato debe estar asociado a un partido político")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partido_id", nullable = false)
    private PartidoPolitico partido;

}
