package com.mobydigital.votaciones.service;

import com.mobydigital.votaciones.dto.CandidatoRequestDTO;
import com.mobydigital.votaciones.dto.CandidatoResponseDTO;
import com.mobydigital.votaciones.entity.Candidato;
import com.mobydigital.votaciones.entity.PartidoPolitico;
import com.mobydigital.votaciones.exception.ResourceNotFoundException;
import com.mobydigital.votaciones.repository.CandidatoRepository;
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
 * Unit tests for CandidatoServiceImpl
 */
@ExtendWith(MockitoExtension.class)
class CandidatoServiceTest {

    @Mock
    private CandidatoRepository candidatoRepository;

    @Mock
    private PartidoPoliticoRepository partidoRepository;

    @InjectMocks
    private CandidatoServiceImpl service;

    private Candidato candidato;
    private PartidoPolitico partido;
    private CandidatoRequestDTO requestDTO;

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

        requestDTO = new CandidatoRequestDTO();
        requestDTO.setNombreCompleto("Juan Perez");
        requestDTO.setPartidoId(1L);
    }

    @Test
    void testCreateCandidatoSuccess() {
        // Arrange
        when(partidoRepository.findById(1L)).thenReturn(Optional.of(partido));
        when(candidatoRepository.save(any(Candidato.class))).thenReturn(candidato);

        // Act
        CandidatoResponseDTO response = service.create(requestDTO);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Juan Perez", response.getNombreCompleto());
        assertEquals("UCR", response.getPartido().getSigla());
        verify(partidoRepository, times(1)).findById(1L);
        verify(candidatoRepository, times(1)).save(any(Candidato.class));
    }

    @Test
    void testCreateCandidatoWithInvalidPartido() {
        // Arrange
        when(partidoRepository.findById(99L)).thenReturn(Optional.empty());
        requestDTO.setPartidoId(99L);

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(
            ResourceNotFoundException.class,
            () -> service.create(requestDTO)
        );

        assertTrue(exception.getMessage().contains("PartidoPolitico"));
        assertTrue(exception.getMessage().contains("99"));
        verify(partidoRepository, times(1)).findById(99L);
        verify(candidatoRepository, never()).save(any(Candidato.class));
    }

}
