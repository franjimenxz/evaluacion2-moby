package com.mobydigital.votaciones.service;

import com.mobydigital.votaciones.dto.CandidatoRequestDTO;
import com.mobydigital.votaciones.dto.CandidatoResponseDTO;
import com.mobydigital.votaciones.dto.PartidoPoliticoResponseDTO;
import com.mobydigital.votaciones.entity.Candidato;
import com.mobydigital.votaciones.entity.PartidoPolitico;
import com.mobydigital.votaciones.exception.ResourceNotFoundException;
import com.mobydigital.votaciones.repository.CandidatoRepository;
import com.mobydigital.votaciones.repository.PartidoPoliticoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementacion del servicio de Candidato
 */
@Service
@RequiredArgsConstructor
public class CandidatoServiceImpl implements CandidatoService {

    private final CandidatoRepository candidatoRepository;
    private final PartidoPoliticoRepository partidoRepository;

    @Override
    @Transactional
    public CandidatoResponseDTO create(CandidatoRequestDTO request) {
        // Validar que el partido existe
        PartidoPolitico partido = partidoRepository.findById(request.getPartidoId())
                .orElseThrow(() -> new ResourceNotFoundException("PartidoPolitico", "id", request.getPartidoId()));

        Candidato candidato = new Candidato();
        candidato.setNombreCompleto(request.getNombreCompleto());
        candidato.setPartido(partido);

        Candidato savedCandidato = candidatoRepository.save(candidato);
        return mapToResponseDTO(savedCandidato);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CandidatoResponseDTO> findAll() {
        return candidatoRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CandidatoResponseDTO findById(Long id) {
        Candidato candidato = candidatoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidato", "id", id));
        return mapToResponseDTO(candidato);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Candidato candidato = candidatoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidato", "id", id));

        candidatoRepository.delete(candidato);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CandidatoResponseDTO> findByPartido(Long partidoId) {
        // Validar que el partido existe
        if (!partidoRepository.existsById(partidoId)) {
            throw new ResourceNotFoundException("PartidoPolitico", "id", partidoId);
        }

        return candidatoRepository.findByPartidoId(partidoId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Mapea una entidad Candidato a su DTO de respuesta
     */
    private CandidatoResponseDTO mapToResponseDTO(Candidato candidato) {
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
