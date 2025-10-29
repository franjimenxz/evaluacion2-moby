package com.mobydigital.votaciones.service;

import com.mobydigital.votaciones.dto.CandidatoRequestDTO;
import com.mobydigital.votaciones.dto.CandidatoResponseDTO;

import java.util.List;

/**
 * Servicio para la logica de negocio de Candidato
 */
public interface CandidatoService {

    /**
     * Crea un nuevo candidato
     */
    CandidatoResponseDTO create(CandidatoRequestDTO request);

    /**
     * Obtiene todos los candidatos
     */
    List<CandidatoResponseDTO> findAll();

    /**
     * Busca un candidato por ID
     */
    CandidatoResponseDTO findById(Long id);

    /**
     * Elimina un candidato por ID
     */
    void delete(Long id);

    /**
     * Busca candidatos por partido
     */
    List<CandidatoResponseDTO> findByPartido(Long partidoId);

}