package com.mobydigital.votaciones.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobydigital.votaciones.dto.CandidatoResponseDTO;
import com.mobydigital.votaciones.dto.PartidoPoliticoResponseDTO;
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

}