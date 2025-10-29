package com.mobydigital.votaciones.service;

import com.mobydigital.votaciones.dto.PartidoPoliticoRequestDTO;
import com.mobydigital.votaciones.dto.PartidoPoliticoResponseDTO;
import com.mobydigital.votaciones.entity.PartidoPolitico;
import com.mobydigital.votaciones.exception.BadRequestException;
import com.mobydigital.votaciones.exception.ResourceNotFoundException;
import com.mobydigital.votaciones.repository.PartidoPoliticoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for PartidoPoliticoServiceImpl
 */
@ExtendWith(MockitoExtension.class)
class PartidoPoliticoServiceTest {

    @Mock
    private PartidoPoliticoRepository repository;

    @InjectMocks
    private PartidoPoliticoServiceImpl service;

    private PartidoPolitico partido;
    private PartidoPoliticoRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        partido = new PartidoPolitico();
        partido.setId(1L);
        partido.setNombre("Union Civica Radical");
        partido.setSigla("UCR");

        requestDTO = new PartidoPoliticoRequestDTO();
        requestDTO.setNombre("Union Civica Radical");
        requestDTO.setSigla("UCR");
    }

}