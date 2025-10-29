package com.mobydigital.votaciones.service;

import com.mobydigital.votaciones.dto.PartidoPoliticoRequestDTO;
import com.mobydigital.votaciones.dto.PartidoPoliticoResponseDTO;
import com.mobydigital.votaciones.entity.PartidoPolitico;
import com.mobydigital.votaciones.exception.BadRequestException;
import com.mobydigital.votaciones.exception.ResourceNotFoundException;
import com.mobydigital.votaciones.repository.PartidoPoliticoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementacion del servicio de PartidoPolitico
 */
@Service
@RequiredArgsConstructor
public class PartidoPoliticoServiceImpl implements PartidoPoliticoService {

    private final PartidoPoliticoRepository repository;

    @Override
    @Transactional
    public PartidoPoliticoResponseDTO create(PartidoPoliticoRequestDTO request) {
        // Validar que no exista un partido con la misma sigla
        if (repository.existsBySigla(request.getSigla())) {
            throw new BadRequestException("Ya existe un partido politico con la sigla: " + request.getSigla());
        }

        PartidoPolitico partido = new PartidoPolitico();
        partido.setNombre(request.getNombre());
        partido.setSigla(request.getSigla());

        PartidoPolitico savedPartido = repository.save(partido);
        return mapToResponseDTO(savedPartido);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PartidoPoliticoResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PartidoPoliticoResponseDTO findById(Long id) {
        PartidoPolitico partido = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PartidoPolitico", "id", id));
        return mapToResponseDTO(partido);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        PartidoPolitico partido = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PartidoPolitico", "id", id));

        repository.delete(partido);
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
