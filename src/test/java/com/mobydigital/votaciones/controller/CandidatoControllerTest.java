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

}