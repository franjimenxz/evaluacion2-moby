package com.mobydigital.votaciones.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entidad que representa un voto emitido en el sistema de votaciones.
 *
 * Cada voto está asociado a un candidato específico y registra la fecha y hora de emisión.
 */
@Entity
@Table(name = "votos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Voto {

    /**
     * Identificador único del voto.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Fecha y hora en que se emitió el voto.
     */
    @Column(nullable = false)
    private LocalDateTime fechaEmision;

}
