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
import java.util.Collections;
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

    @Test
    void testRegistrarVotoSuccess() {
        // Preparar
        when(candidatoRepository.findById(1L)).thenReturn(Optional.of(candidato));
        when(votoRepository.save(any(Voto.class))).thenReturn(voto);

        // Actuar
        VotoResponseDTO response = service.registrarVoto(requestDTO);

        // Verificar
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Juan Perez", response.getCandidato().getNombreCompleto());
        assertEquals("UCR", response.getCandidato().getPartido().getSigla());
        assertNotNull(response.getFechaEmision());
        verify(candidatoRepository, times(1)).findById(1L);
        verify(votoRepository, times(1)).save(any(Voto.class));
    }

    @Test
    void testRegistrarVotoWithInvalidCandidato() {
        // Preparar
        when(candidatoRepository.findById(99L)).thenReturn(Optional.empty());
        requestDTO.setCandidatoId(99L);

        // Actuar y Verificar
        ResourceNotFoundException exception = assertThrows(
            ResourceNotFoundException.class,
            () -> service.registrarVoto(requestDTO)
        );

        assertTrue(exception.getMessage().contains("Candidato"));
        assertTrue(exception.getMessage().contains("99"));
        verify(candidatoRepository, times(1)).findById(99L);
        verify(votoRepository, never()).save(any(Voto.class));
    }

    @Test
    void testFindAllVotos() {
        // Preparar
        Voto voto2 = new Voto();
        voto2.setId(2L);
        voto2.setCandidato(candidato);
        voto2.setFechaEmision(LocalDateTime.now());

        List<Voto> votos = Arrays.asList(voto, voto2);
        when(votoRepository.findAll()).thenReturn(votos);

        // Actuar
        List<VotoResponseDTO> response = service.findAll();

        // Verificar
        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals(1L, response.get(0).getId());
        assertEquals(2L, response.get(1).getId());
        verify(votoRepository, times(1)).findAll();
    }

    @Test
    void testCountByCandidato() {
        // Preparar
        when(candidatoRepository.existsById(1L)).thenReturn(true);
        when(votoRepository.countByCandidatoId(1L)).thenReturn(150L);

        // Actuar
        Long count = service.countByCandidato(1L);

        // Verificar
        assertNotNull(count);
        assertEquals(150L, count);
        verify(candidatoRepository, times(1)).existsById(1L);
        verify(votoRepository, times(1)).countByCandidatoId(1L);
    }

    @Test
    void testCountByPartido() {
        // Preparar
        when(votoRepository.countByPartidoId(1L)).thenReturn(300L);

        // Actuar
        Long count = service.countByPartido(1L);

        // Verificar
        assertNotNull(count);
        assertEquals(300L, count);
        verify(votoRepository, times(1)).countByPartidoId(1L);
    }

    @Test
    void testGetEstadisticasPorCandidato() {
        // Preparar
        Candidato candidato2 = new Candidato();
        candidato2.setId(2L);
        candidato2.setNombreCompleto("Maria Lopez");
        candidato2.setPartido(partido);

        List<Candidato> candidatos = Arrays.asList(candidato, candidato2);
        when(candidatoRepository.findAll()).thenReturn(candidatos);
        when(votoRepository.countByCandidatoId(1L)).thenReturn(100L);
        when(votoRepository.countByCandidatoId(2L)).thenReturn(80L);

        // Actuar
        List<VotosCountDTO> response = service.getEstadisticasPorCandidato();

        // Verificar
        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals("Juan Perez", response.get(0).getNombre());
        assertEquals(100L, response.get(0).getCantidadVotos());
        assertEquals("Maria Lopez", response.get(1).getNombre());
        assertEquals(80L, response.get(1).getCantidadVotos());
        verify(candidatoRepository, times(1)).findAll();
    }

    @Test
    void testGetEstadisticasPorPartido() {
        // Preparar
        Object[] resultado1 = new Object[]{partido, 200L};
        List<Object[]> resultados = Collections.singletonList(resultado1);
        when(votoRepository.countVotosByPartido()).thenReturn(resultados);

        // Actuar
        List<VotosCountDTO> response = service.getEstadisticasPorPartido();

        // Verificar
        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals("Union Civica Radical", response.get(0).getNombre());
        assertEquals(200L, response.get(0).getCantidadVotos());
        verify(votoRepository, times(1)).countVotosByPartido();
    }

}
