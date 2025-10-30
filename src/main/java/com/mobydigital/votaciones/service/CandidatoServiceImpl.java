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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementacion del servicio de Candidato
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CandidatoServiceImpl implements CandidatoService {

    private final CandidatoRepository candidatoRepository;
    private final PartidoPoliticoRepository partidoRepository;

    @Override
    @Transactional
    public CandidatoResponseDTO create(CandidatoRequestDTO request) {
        log.info("Intentando crear candidato {} para partido id {}", request.getNombreCompleto(), request.getPartidoId());

        // Validar que el partido existe
        PartidoPolitico partido = partidoRepository.findById(request.getPartidoId())
                .orElseThrow(() -> {
                    log.warn("No se puede crear candidato porque el partido id {} no existe", request.getPartidoId());
                    return new ResourceNotFoundException("PartidoPolitico", "id", request.getPartidoId());
                });

        Candidato candidato = new Candidato();
        candidato.setNombreCompleto(request.getNombreCompleto());
        candidato.setPartido(partido);

        Candidato savedCandidato = candidatoRepository.save(candidato);
        log.info("Candidato creado exitosamente id {} nombre {} partido {}",
                savedCandidato.getId(), savedCandidato.getNombreCompleto(), partido.getSigla());
        return mapToResponseDTO(savedCandidato);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CandidatoResponseDTO> findAll() {
        log.info("Consultando todos los candidatos");
        List<CandidatoResponseDTO> candidatos = candidatoRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .toList();
        log.info("Se encontraron {} candidatos", candidatos.size());
        return candidatos;
    }

    @Override
    @Transactional(readOnly = true)
    public CandidatoResponseDTO findById(Long id) {
        log.info("Buscando candidato con id {}", id);
        Candidato candidato = candidatoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("No se encontro candidato con id {}", id);
                    return new ResourceNotFoundException("Candidato", "id", id);
                });
        log.info("Candidato encontrado id {} nombre {}", candidato.getId(), candidato.getNombreCompleto());
        return mapToResponseDTO(candidato);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Intentando eliminar candidato con id {}", id);
        Candidato candidato = candidatoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("No se puede eliminar candidato con id {} porque no existe", id);
                    return new ResourceNotFoundException("Candidato", "id", id);
                });

        candidatoRepository.delete(candidato);
        log.info("Candidato eliminado exitosamente id {} nombre {}", candidato.getId(), candidato.getNombreCompleto());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CandidatoResponseDTO> findByPartido(Long partidoId) {
        log.info("Consultando candidatos del partido id {}", partidoId);

        // Validar que el partido existe
        if (!partidoRepository.existsById(partidoId)) {
            log.warn("No se pueden consultar candidatos porque el partido id {} no existe", partidoId);
            throw new ResourceNotFoundException("PartidoPolitico", "id", partidoId);
        }

        List<CandidatoResponseDTO> candidatos = candidatoRepository.findByPartidoId(partidoId).stream()
                .map(this::mapToResponseDTO)
                .toList();
        log.info("Se encontraron {} candidatos para el partido id {}", candidatos.size(), partidoId);
        return candidatos;
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
