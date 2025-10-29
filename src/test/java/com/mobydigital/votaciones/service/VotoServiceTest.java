package com.mobydigital.votaciones.service;

import com.mobydigital.votaciones.dto.VotoRequestDTO;
import com.mobydigital.votaciones.dto.VotoResponseDTO;
import com.mobydigital.votaciones.dto.VotosCountDTO;
import com.mobydigital.votaciones.entity.Candidato;
import com.mobydigital.votaciones.entity.PartidoPolitico;
import com.mobydigital.votaciones.entity.Voto;
import com.mobydigital.votaciones.exception.ResourceNotFoundException;
import com.mobydigital.votaciones.repository.CandidatoRepository;
import com.mobydigital.votaciones.repository.VotoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for VotoServiceImpl
 */
@ExtendWith(MockitoExtension.class)
class VotoServiceTest {

    @Mock
    private VotoRepository votoRepository;

    @Mock
    private CandidatoRepository candidatoRepository;

    @InjectMocks
    private VotoServiceImpl service;

    private Voto voto;
    private Candidato candidato;
    private PartidoPolitico partido;
    private VotoRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        partido = new PartidoPolitico();
        partido.setId(1L);
        partido.setNombre("Union Civica Radical");
        partido.setSigla("UCR");

        candidato = new Candidato();
        candidato.setId(1L);
        candidato.setNombreCompleto("Juan Perez");
        candidato.setPartido(partido);

        voto = new Voto();
        voto.setId(1L);
        voto.setCandidato(candidato);
        voto.setFechaEmision(LocalDateTime.now());

        requestDTO = new VotoRequestDTO();
        requestDTO.setCandidatoId(1L);
    }

}
