package com.mobydigital.votaciones.service;

import com.mobydigital.votaciones.dto.PartidoPoliticoRequestDTO;
import com.mobydigital.votaciones.dto.PartidoPoliticoResponseDTO;
import com.mobydigital.votaciones.entity.PartidoPolitico;
import com.mobydigital.votaciones.exception.BadRequestException;
import com.mobydigital.votaciones.exception.ResourceNotFoundException;
import com.mobydigital.votaciones.repository.PartidoPoliticoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementacion del servicio de PartidoPolitico
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PartidoPoliticoServiceImpl implements PartidoPoliticoService {

    private final PartidoPoliticoRepository repository;

    @Override
    @Transactional
    public PartidoPoliticoResponseDTO create(PartidoPoliticoRequestDTO request) {
        log.info("Intentando crear partido politico con sigla {}", request.getSigla());

        // Validar que no exista un partido con la misma sigla
        if (repository.existsBySigla(request.getSigla())) {
            log.warn("Sigla {} ya existe rechazando creacion del partido", request.getSigla());
            throw new BadRequestException("Ya existe un partido politico con la sigla: " + request.getSigla());
        }

        PartidoPolitico partido = new PartidoPolitico();
        partido.setNombre(request.getNombre());
        partido.setSigla(request.getSigla());

        PartidoPolitico savedPartido = repository.save(partido);
        log.info("Partido politico creado exitosamente con id {} sigla {}", savedPartido.getId(), savedPartido.getSigla());
        return mapToResponseDTO(savedPartido);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PartidoPoliticoResponseDTO> findAll() {
        log.info("Consultando todos los partidos politicos");
        List<PartidoPoliticoResponseDTO> partidos = repository.findAll().stream()
                .map(this::mapToResponseDTO)
                .toList();
        log.info("Se encontraron {} partidos politicos", partidos.size());
        return partidos;
    }

    @Override
    @Transactional(readOnly = true)
    public PartidoPoliticoResponseDTO findById(Long id) {
        log.info("Buscando partido politico con id {}", id);
        PartidoPolitico partido = repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("No se encontro partido politico con id {}", id);
                    return new ResourceNotFoundException("PartidoPolitico", "id", id);
                });
        log.info("Partido politico encontrado id {} sigla {}", partido.getId(), partido.getSigla());
        return mapToResponseDTO(partido);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Intentando eliminar partido politico con id {}", id);
        PartidoPolitico partido = repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("No se puede eliminar partido politico con id {} porque no existe", id);
                    return new ResourceNotFoundException("PartidoPolitico", "id", id);
                });

        repository.delete(partido);
        log.info("Partido politico eliminado exitosamente id {} sigla {}", partido.getId(), partido.getSigla());
    }

    /**
     * Mapea una entidad PartidoPolitico a su DTO de respuesta
     */
    private PartidoPoliticoResponseDTO mapToResponseDTO(PartidoPolitico partido) {
        PartidoPoliticoResponseDTO dto = new PartidoPoliticoResponseDTO();
        dto.setId(partido.getId());
        dto.setNombre(partido.getNombre());
        dto.setSigla(partido.getSigla());
        return dto;
    }

}
