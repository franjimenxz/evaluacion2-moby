package com.mobydigital.votaciones.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobydigital.votaciones.dto.PartidoPoliticoRequestDTO;
import com.mobydigital.votaciones.dto.PartidoPoliticoResponseDTO;
import com.mobydigital.votaciones.exception.ResourceNotFoundException;
import com.mobydigital.votaciones.service.PartidoPoliticoService;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for PartidoPoliticoController
 */
@WebMvcTest(PartidoPoliticoController.class)
class PartidoPoliticoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PartidoPoliticoService service;

    private PartidoPoliticoRequestDTO requestDTO;
    private PartidoPoliticoResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        requestDTO = new PartidoPoliticoRequestDTO();
        requestDTO.setNombre("Union Civica Radical");
        requestDTO.setSigla("UCR");

        responseDTO = new PartidoPoliticoResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setNombre("Union Civica Radical");
        responseDTO.setSigla("UCR");
    }

    @Test
    void testCreatePartido() throws Exception {
        // Arrange
        when(service.create(any(PartidoPoliticoRequestDTO.class))).thenReturn(responseDTO);

        // Act & Assert
        mockMvc.perform(post("/api/partidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Union Civica Radical"))
                .andExpect(jsonPath("$.sigla").value("UCR"));
    }

    @Test
    void testGetAllPartidos() throws Exception {
        // Arrange
        PartidoPoliticoResponseDTO responseDTO2 = new PartidoPoliticoResponseDTO();
        responseDTO2.setId(2L);
        responseDTO2.setNombre("Propuesta Republicana");
        responseDTO2.setSigla("PRO");

        List<PartidoPoliticoResponseDTO> partidos = Arrays.asList(responseDTO, responseDTO2);
        when(service.findAll()).thenReturn(partidos);

        // Act & Assert
        mockMvc.perform(get("/api/partidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].sigla").value("UCR"))
                .andExpect(jsonPath("$[1].sigla").value("PRO"));
    }

}
