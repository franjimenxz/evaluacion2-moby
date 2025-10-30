package com.mobydigital.votaciones.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobydigital.votaciones.dto.CandidatoResponseDTO;
import com.mobydigital.votaciones.dto.PartidoPoliticoResponseDTO;
import com.mobydigital.votaciones.dto.VotoCountResponseDTO;
import com.mobydigital.votaciones.dto.VotoRequestDTO;
import com.mobydigital.votaciones.dto.VotoResponseDTO;
import com.mobydigital.votaciones.dto.VotosCountDTO;
import com.mobydigital.votaciones.service.VotoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for VotoController
 */
@WebMvcTest(VotoController.class)
class VotoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VotoService service;

    private VotoRequestDTO requestDTO;
    private VotoResponseDTO responseDTO;
    private CandidatoResponseDTO candidatoDTO;
    private PartidoPoliticoResponseDTO partidoDTO;

    @BeforeEach
    void setUp() {
        partidoDTO = new PartidoPoliticoResponseDTO();
        partidoDTO.setId(1L);
        partidoDTO.setNombre("Union Civica Radical");
        partidoDTO.setSigla("UCR");

        candidatoDTO = new CandidatoResponseDTO();
        candidatoDTO.setId(1L);
        candidatoDTO.setNombreCompleto("Juan Perez");
        candidatoDTO.setPartido(partidoDTO);

        requestDTO = new VotoRequestDTO();
        requestDTO.setCandidatoId(1L);

        responseDTO = new VotoResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setCandidato(candidatoDTO);
        responseDTO.setFechaEmision(LocalDateTime.now());
    }

    @Test
    void testRegistrarVoto() throws Exception {
        // Preparar
        when(service.registrarVoto(any(VotoRequestDTO.class))).thenReturn(responseDTO);

        // Actuar y Verificar
        mockMvc.perform(post("/api/votos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.candidato.nombreCompleto").value("Juan Perez"))
                .andExpect(jsonPath("$.candidato.partido.sigla").value("UCR"));
    }

    @Test
    void testGetAllVotos() throws Exception {
        // Preparar
        VotoResponseDTO responseDTO2 = new VotoResponseDTO();
        responseDTO2.setId(2L);
        responseDTO2.setCandidato(candidatoDTO);
        responseDTO2.setFechaEmision(LocalDateTime.now());

        List<VotoResponseDTO> votos = Arrays.asList(responseDTO, responseDTO2);
        when(service.findAll()).thenReturn(votos);

        // Actuar y Verificar
        mockMvc.perform(get("/api/votos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }

    @Test
    void testCountByCandidato() throws Exception {
        // Preparar
        VotoCountResponseDTO countResponse = new VotoCountResponseDTO();
        countResponse.setId(1L);
        countResponse.setNombre("Martin Lousteau");
        countResponse.setCantidadVotos(150L);

        when(service.countByCandidatoDetallado(1L)).thenReturn(countResponse);

        // Actuar y Verificar
        mockMvc.perform(get("/api/votos/count/candidato/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Martin Lousteau"))
                .andExpect(jsonPath("$.cantidadVotos").value(150));
    }

    @Test
    void testCountByPartido() throws Exception {
        // Preparar
        VotoCountResponseDTO countResponse = new VotoCountResponseDTO();
        countResponse.setId(1L);
        countResponse.setNombre("Union Civica Radical");
        countResponse.setCantidadVotos(300L);

        when(service.countByPartidoDetallado(1L)).thenReturn(countResponse);

        // Actuar y Verificar
        mockMvc.perform(get("/api/votos/count/partido/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Union Civica Radical"))
                .andExpect(jsonPath("$.cantidadVotos").value(300));
    }

    @Test
    void testGetEstadisticasPorCandidato() throws Exception {
        // Preparar
        VotosCountDTO stats1 = new VotosCountDTO();
        stats1.setId(1L);
        stats1.setNombre("Juan Perez");
        stats1.setCantidadVotos(100L);

        VotosCountDTO stats2 = new VotosCountDTO();
        stats2.setId(2L);
        stats2.setNombre("Maria Lopez");
        stats2.setCantidadVotos(80L);

        List<VotosCountDTO> estadisticas = Arrays.asList(stats1, stats2);
        when(service.getEstadisticasPorCandidato()).thenReturn(estadisticas);

        // Actuar y Verificar
        mockMvc.perform(get("/api/votos/estadisticas/candidatos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nombre").value("Juan Perez"))
                .andExpect(jsonPath("$[0].cantidadVotos").value(100))
                .andExpect(jsonPath("$[1].nombre").value("Maria Lopez"))
                .andExpect(jsonPath("$[1].cantidadVotos").value(80));
    }

    @Test
    void testGetEstadisticasPorPartido() throws Exception {
        // Preparar
        VotosCountDTO stats1 = new VotosCountDTO();
        stats1.setId(1L);
        stats1.setNombre("Union Civica Radical");
        stats1.setCantidadVotos(200L);

        List<VotosCountDTO> estadisticas = Arrays.asList(stats1);
        when(service.getEstadisticasPorPartido()).thenReturn(estadisticas);

        // Actuar y Verificar
        mockMvc.perform(get("/api/votos/estadisticas/partidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Union Civica Radical"))
                .andExpect(jsonPath("$[0].cantidadVotos").value(200));
    }

    @Test
    void testRegistrarVotoWithValidationErrors() throws Exception {
        // Preparar
        VotoRequestDTO invalidRequest = new VotoRequestDTO();
        invalidRequest.setCandidatoId(null); // Null candidato

        // Actuar y Verificar
        mockMvc.perform(post("/api/votos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

}