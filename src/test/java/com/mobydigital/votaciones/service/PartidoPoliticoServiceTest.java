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

import java.util.Arrays;
import java.util.List;
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

    @Test
    void testCreatePartidoSuccess() {
        // Arrange
        when(repository.existsBySigla("UCR")).thenReturn(false);
        when(repository.save(any(PartidoPolitico.class))).thenReturn(partido);

        // Act
        PartidoPoliticoResponseDTO response = service.create(requestDTO);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Union Civica Radical", response.getNombre());
        assertEquals("UCR", response.getSigla());
        verify(repository, times(1)).existsBySigla("UCR");
        verify(repository, times(1)).save(any(PartidoPolitico.class));
    }

    @Test
    void testCreatePartidoWithDuplicateSigla() {
        // Arrange
        when(repository.existsBySigla("UCR")).thenReturn(true);

        // Act & Assert
        BadRequestException exception = assertThrows(
            BadRequestException.class,
            () -> service.create(requestDTO)
        );

        assertEquals("Ya existe un partido con la sigla UCR", exception.getMessage());
        verify(repository, times(1)).existsBySigla("UCR");
        verify(repository, never()).save(any(PartidoPolitico.class));
    }

    @Test
    void testFindAllPartidos() {
        // Arrange
        PartidoPolitico partido2 = new PartidoPolitico();
        partido2.setId(2L);
        partido2.setNombre("Propuesta Republicana");
        partido2.setSigla("PRO");

        List<PartidoPolitico> partidos = Arrays.asList(partido, partido2);
        when(repository.findAll()).thenReturn(partidos);

        // Act
        List<PartidoPoliticoResponseDTO> response = service.findAll();

        // Assert
        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals("UCR", response.get(0).getSigla());
        assertEquals("PRO", response.get(1).getSigla());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testFindByIdPartidoExists() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(partido));

        // Act
        PartidoPoliticoResponseDTO response = service.findById(1L);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Union Civica Radical", response.getNombre());
        assertEquals("UCR", response.getSigla());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testFindByIdPartidoNotFound() {
        // Arrange
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(
            ResourceNotFoundException.class,
            () -> service.findById(99L)
        );

        assertTrue(exception.getMessage().contains("PartidoPolitico"));
        assertTrue(exception.getMessage().contains("99"));
        verify(repository, times(1)).findById(99L);
    }

    @Test
    void testDeletePartidoSuccess() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(partido));
        doNothing().when(repository).delete(partido);

        // Act
        service.delete(1L);

        // Assert
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).delete(partido);
    }

}