package com.mobydigital.votaciones.service;

import com.mobydigital.votaciones.dto.PartidoPoliticoRequestDTO;
import com.mobydigital.votaciones.dto.PartidoPoliticoResponseDTO;

import java.util.List;

/**
 * Servicio para la logica de negocio de PartidoPolitico
 */
public interface PartidoPoliticoService {

    /**
     * Crea un nuevo partido politico
     */
    PartidoPoliticoResponseDTO create(PartidoPoliticoRequestDTO request);

    /**
     * Obtiene todos los partidos politicos
     */
    List<PartidoPoliticoResponseDTO> findAll();

    /**
     * Busca un partido politico por ID
     */
    PartidoPoliticoResponseDTO findById(Long id);

    /**
     * Elimina un partido politico por ID
     */
    void delete(Long id);

}