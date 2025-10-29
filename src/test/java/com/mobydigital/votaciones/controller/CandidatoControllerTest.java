package com.mobydigital.votaciones.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobydigital.votaciones.dto.CandidatoRequestDTO;
import com.mobydigital.votaciones.dto.CandidatoResponseDTO;
import com.mobydigital.votaciones.dto.PartidoPoliticoResponseDTO;
import com.mobydigital.votaciones.service.CandidatoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for CandidatoController
 */
@WebMvcTest(CandidatoController.class)
class CandidatoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CandidatoService service;

    private CandidatoRequestDTO requestDTO;
    private CandidatoResponseDTO responseDTO;
    private PartidoPoliticoResponseDTO partidoDTO;

    @BeforeEach
    void setUp() {
        partidoDTO = new PartidoPoliticoResponseDTO();
        partidoDTO.setId(1L);
        partidoDTO.setNombre("Union Civica Radical");
        partidoDTO.setSigla("UCR");

        requestDTO = new CandidatoRequestDTO();
        requestDTO.setNombreCompleto("Juan Perez");
        requestDTO.setPartidoId(1L);

        responseDTO = new CandidatoResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setNombreCompleto("Juan Perez");
        responseDTO.setPartido(partidoDTO);
    }

    @Test
    void testCreateCandidato() throws Exception {
        // Preparar
        when(service.create(any(CandidatoRequestDTO.class))).thenReturn(responseDTO);

        // Actuar y Verificar
        mockMvc.perform(post("/api/candidatos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombreCompleto").value("Juan Perez"))
                .andExpect(jsonPath("$.partido.sigla").value("UCR"));
    }

    @Test
    void testGetAllCandidatos() throws Exception {
        // Preparar
        CandidatoResponseDTO responseDTO2 = new CandidatoResponseDTO();
        responseDTO2.setId(2L);
        responseDTO2.setNombreCompleto("Maria Lopez");
        responseDTO2.setPartido(partidoDTO);

        List<CandidatoResponseDTO> candidatos = Arrays.asList(responseDTO, responseDTO2);
        when(service.findAll()).thenReturn(candidatos);

        // Actuar y Verificar
        mockMvc.perform(get("/api/candidatos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nombreCompleto").value("Juan Perez"))
                .andExpect(jsonPath("$[1].nombreCompleto").value("Maria Lopez"));
    }

    @Test
    void testGetCandidatoById() throws Exception {
        // Preparar
        when(service.findById(1L)).thenReturn(responseDTO);

        // Actuar y Verificar
        mockMvc.perform(get("/api/candidatos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombreCompleto").value("Juan Perez"))
                .andExpect(jsonPath("$.partido.sigla").value("UCR"));
    }

    @Test
    void testDeleteCandidato() throws Exception {
        // Preparar
        doNothing().when(service).delete(1L);

        // Actuar y Verificar
        mockMvc.perform(delete("/api/candidatos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetCandidatosByPartido() throws Exception {
        // Preparar
        CandidatoResponseDTO responseDTO2 = new CandidatoResponseDTO();
        responseDTO2.setId(2L);
        responseDTO2.setNombreCompleto("Maria Lopez");
        responseDTO2.setPartido(partidoDTO);

        List<CandidatoResponseDTO> candidatos = Arrays.asList(responseDTO, responseDTO2);
        when(service.findByPartido(1L)).thenReturn(candidatos);

        // Actuar y Verificar
        mockMvc.perform(get("/api/candidatos/partido/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nombreCompleto").value("Juan Perez"))
                .andExpect(jsonPath("$[1].nombreCompleto").value("Maria Lopez"));
    }

    @Test
    void testCreateCandidatoWithValidationErrors() throws Exception {
        // Preparar
        CandidatoRequestDTO invalidRequest = new CandidatoRequestDTO();
        invalidRequest.setNombreCompleto(""); // Empty name
        invalidRequest.setPartidoId(null); // Null partido

        // Actuar y Verificar
        mockMvc.perform(post("/api/candidatos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

}