package com.mobydigital.votaciones.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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
     * Candidato al que se le emitió el voto.
     * Relación ManyToOne: Muchos votos pueden ser emitidos a un mismo candidato.
     */
    @NotNull(message = "El voto debe estar asociado a un candidato")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidato_id", nullable = false)
    private Candidato candidato;

    /**
     * Fecha y hora en que se emitió el voto.
     * Debe ser una fecha pasada o presente, no futura.
     */
    @NotNull(message = "La fecha de emisión del voto no puede ser nula")
    @PastOrPresent(message = "La fecha de emisión debe ser pasada o presente")
    @Column(nullable = false)
    private LocalDateTime fechaEmision;

}
