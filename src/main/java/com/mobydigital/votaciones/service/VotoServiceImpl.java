package com.mobydigital.votaciones.service;

import com.mobydigital.votaciones.dto.CandidatoResponseDTO;
import com.mobydigital.votaciones.dto.PartidoPoliticoResponseDTO;
import com.mobydigital.votaciones.dto.VotoCountResponseDTO;
import com.mobydigital.votaciones.dto.VotoRequestDTO;
import com.mobydigital.votaciones.dto.VotoResponseDTO;
import com.mobydigital.votaciones.dto.VotosCountDTO;
import com.mobydigital.votaciones.entity.Candidato;
import com.mobydigital.votaciones.entity.PartidoPolitico;
import com.mobydigital.votaciones.entity.Voto;
import com.mobydigital.votaciones.exception.ResourceNotFoundException;
import com.mobydigital.votaciones.repository.CandidatoRepository;
import com.mobydigital.votaciones.repository.PartidoPoliticoRepository;
import com.mobydigital.votaciones.repository.VotoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementacion del servicio de Voto
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class VotoServiceImpl implements VotoService {

    private final VotoRepository votoRepository;
    private final CandidatoRepository candidatoRepository;
    private final PartidoPoliticoRepository partidoRepository;

    @Override
    @Transactional
    public VotoResponseDTO registrarVoto(VotoRequestDTO request) {
        log.info("Registrando voto para candidato id {}", request.getCandidatoId());

        // Validar que el candidato existe
        Candidato candidato = candidatoRepository.findById(request.getCandidatoId())
                .orElseThrow(() -> {
                    log.warn("No se puede registrar voto porque el candidato id {} no existe", request.getCandidatoId());
                    return new ResourceNotFoundException("Candidato", "id", request.getCandidatoId());
                });

        Voto voto = new Voto();
        voto.setCandidato(candidato);
        voto.setFechaEmision(LocalDateTime.now());

        Voto savedVoto = votoRepository.save(voto);
        log.info("Voto registrado exitosamente id {} para candidato {} del partido {}",
                savedVoto.getId(), candidato.getNombreCompleto(), candidato.getPartido().getSigla());
        return mapToResponseDTO(savedVoto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VotoResponseDTO> findAll() {
        log.info("Consultando todos los votos");
        List<VotoResponseDTO> votos = votoRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .toList();
        log.info("Se encontraron {} votos registrados", votos.size());
        return votos;
    }

    @Override
    @Transactional(readOnly = true)
    public Long countByCandidato(Long candidatoId) {
        log.info("Contando votos del candidato id {}", candidatoId);

        // Validar que el candidato existe
        if (!candidatoRepository.existsById(candidatoId)) {
            log.warn("No se pueden contar votos porque el candidato id {} no existe", candidatoId);
            throw new ResourceNotFoundException("Candidato", "id", candidatoId);
        }

        Long count = votoRepository.countByCandidatoId(candidatoId);
        log.info("El candidato id {} tiene {} votos", candidatoId, count);
        return count;
    }

    @Override
    @Transactional(readOnly = true)
    public Long countByPartido(Long partidoId) {
        log.info("Contando votos del partido id {}", partidoId);
        Long count = votoRepository.countByPartidoId(partidoId);
        log.info("El partido id {} tiene {} votos", partidoId, count);
        return count;
    }

    @Override
    @Transactional(readOnly = true)
    public VotoCountResponseDTO countByCandidatoDetallado(Long candidatoId) {
        log.info("Obteniendo conteo detallado de votos del candidato id {}", candidatoId);

        Candidato candidato = candidatoRepository.findById(candidatoId)
                .orElseThrow(() -> {
                    log.warn("No se puede obtener conteo porque el candidato id {} no existe", candidatoId);
                    return new ResourceNotFoundException("Candidato", "id", candidatoId);
                });

        Long count = votoRepository.countByCandidatoId(candidatoId);
        log.info("Candidato {} tiene {} votos", candidato.getNombreCompleto(), count);

        return new VotoCountResponseDTO(
                candidato.getId(),
                candidato.getNombreCompleto(),
                count
        );
    }

    @Override
    @Transactional(readOnly = true)
    public VotoCountResponseDTO countByPartidoDetallado(Long partidoId) {
        log.info("Obteniendo conteo detallado de votos del partido id {}", partidoId);

        PartidoPolitico partido = partidoRepository.findById(partidoId)
                .orElseThrow(() -> {
                    log.warn("No se puede obtener conteo porque el partido id {} no existe", partidoId);
                    return new ResourceNotFoundException("PartidoPolitico", "id", partidoId);
                });

        Long count = votoRepository.countByPartidoId(partidoId);
        log.info("Partido {} tiene {} votos", partido.getNombre(), count);

        return new VotoCountResponseDTO(
                partido.getId(),
                partido.getNombre(),
                count
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<VotosCountDTO> getEstadisticasPorCandidato() {
        log.info("Generando estadisticas de votos por candidato");
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

        log.info("Estadisticas generadas para {} candidatos", estadisticas.size());
        return estadisticas;
    }

    @Override
    @Transactional(readOnly = true)
    public List<VotosCountDTO> getEstadisticasPorPartido() {
        log.info("Generando estadisticas de votos por partido");
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

        log.info("Estadisticas generadas para {} partidos", estadisticas.size());
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