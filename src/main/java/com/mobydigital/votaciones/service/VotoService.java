package com.mobydigital.votaciones.service;

import com.mobydigital.votaciones.dto.VotoCountResponseDTO;
import com.mobydigital.votaciones.dto.VotoRequestDTO;
import com.mobydigital.votaciones.dto.VotoResponseDTO;
import com.mobydigital.votaciones.dto.VotosCountDTO;

import java.util.List;

/**
 * Servicio para la logica de negocio de Voto
 */
public interface VotoService {

    /**
     * Registra un nuevo voto
     */
    VotoResponseDTO registrarVoto(VotoRequestDTO request);

    /**
     * Obtiene todos los votos
     */
    List<VotoResponseDTO> findAll();

    /**
     * Cuenta los votos de un candidato especifico
     */
    Long countByCandidato(Long candidatoId);

    /**
     * Cuenta los votos de un partido especifico
     */
    Long countByPartido(Long partidoId);

    /**
     * Obtiene el conteo de votos de un candidato con informacion estructurada
     */
    VotoCountResponseDTO countByCandidatoDetallado(Long candidatoId);

    /**
     * Obtiene el conteo de votos de un partido con informacion estructurada
     */
    VotoCountResponseDTO countByPartidoDetallado(Long partidoId);

    /**
     * Obtiene estadisticas de votos por candidato
     */
    List<VotosCountDTO> getEstadisticasPorCandidato();

    /**
     * Obtiene estadisticas de votos por partido
     */
    List<VotosCountDTO> getEstadisticasPorPartido();

}
