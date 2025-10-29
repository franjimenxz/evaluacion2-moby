package com.mobydigital.votaciones.service;

import com.mobydigital.votaciones.dto.CandidatoResponseDTO;
import com.mobydigital.votaciones.dto.PartidoPoliticoResponseDTO;
import com.mobydigital.votaciones.dto.VotoRequestDTO;
import com.mobydigital.votaciones.dto.VotoResponseDTO;
import com.mobydigital.votaciones.dto.VotosCountDTO;
import com.mobydigital.votaciones.entity.Candidato;
import com.mobydigital.votaciones.entity.PartidoPolitico;
import com.mobydigital.votaciones.entity.Voto;
import com.mobydigital.votaciones.exception.ResourceNotFoundException;
import com.mobydigital.votaciones.repository.CandidatoRepository;
import com.mobydigital.votaciones.repository.VotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementacion del servicio de Voto
 */
@Service
@RequiredArgsConstructor
public class VotoServiceImpl implements VotoService {

    private final VotoRepository votoRepository;
    private final CandidatoRepository candidatoRepository;

    @Override
    @Transactional
    public VotoResponseDTO registrarVoto(VotoRequestDTO request) {
        // Validar que el candidato existe
        Candidato candidato = candidatoRepository.findById(request.getCandidatoId())
                .orElseThrow(() -> new ResourceNotFoundException("Candidato", "id", request.getCandidatoId()));

        Voto voto = new Voto();
        voto.setCandidato(candidato);
        voto.setFechaEmision(LocalDateTime.now());

        Voto savedVoto = votoRepository.save(voto);
        return mapToResponseDTO(savedVoto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VotoResponseDTO> findAll() {
        return votoRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Long countByCandidato(Long candidatoId) {
        // Validar que el candidato existe
        if (!candidatoRepository.existsById(candidatoId)) {
            throw new ResourceNotFoundException("Candidato", "id", candidatoId);
        }

        return votoRepository.countByCandidatoId(candidatoId);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countByPartido(Long partidoId) {
        return votoRepository.countByPartidoId(partidoId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VotosCountDTO> getEstadisticasPorCandidato() {
        List<Candidato> candidatos = candidatoRepository.findAll();
        List<VotosCountDTO> estadisticas = new ArrayList<>();

        for (Candidato candidato : candidatos) {
            Long cantidadVotos = votoRepository.countByCandidatoId(candidato.getId());
            VotosCountDTO dto = new VotosCountDTO();
            dto.setId(candidato.getId());
            dto.setNombre(candidato.getNombreCompleto());
            dto.setCantidadVotos(cantidadVotos);
            estadisticas.add(dto);
        }

        return estadisticas;
    }

    @Override
    @Transactional(readOnly = true)
    public List<VotosCountDTO> getEstadisticasPorPartido() {
        List<Object[]> resultados = votoRepository.countVotosByPartido();
        List<VotosCountDTO> estadisticas = new ArrayList<>();

        for (Object[] resultado : resultados) {
            PartidoPolitico partido = (PartidoPolitico) resultado[0];
            Long cantidadVotos = (Long) resultado[1];

            VotosCountDTO dto = new VotosCountDTO();
            dto.setId(partido.getId());
            dto.setNombre(partido.getNombre());
            dto.setCantidadVotos(cantidadVotos);
            estadisticas.add(dto);
        }

        return estadisticas;
    }

    /**
     * Mapea una entidad Voto a su DTO de respuesta
     */
    private VotoResponseDTO mapToResponseDTO(Voto voto) {
        VotoResponseDTO dto = new VotoResponseDTO();
        dto.setId(voto.getId());
        dto.setCandidato(mapCandidatoToDTO(voto.getCandidato()));
        dto.setFechaEmision(voto.getFechaEmision());
        return dto;
    }

    /**
     * Mapea una entidad Candidato a su DTO
     */
    private CandidatoResponseDTO mapCandidatoToDTO(Candidato candidato) {
        CandidatoResponseDTO dto = new CandidatoResponseDTO();
        dto.setId(candidato.getId());
        dto.setNombreCompleto(candidato.getNombreCompleto());
        dto.setPartido(mapPartidoToDTO(candidato.getPartido()));
        return dto;
    }

    /**
     * Mapea una entidad PartidoPolitico a su DTO
     */
    private PartidoPoliticoResponseDTO mapPartidoToDTO(PartidoPolitico partido) {
        PartidoPoliticoResponseDTO dto = new PartidoPoliticoResponseDTO();
        dto.setId(partido.getId());
        dto.setNombre(partido.getNombre());
        dto.setSigla(partido.getSigla());
        return dto;
    }

}