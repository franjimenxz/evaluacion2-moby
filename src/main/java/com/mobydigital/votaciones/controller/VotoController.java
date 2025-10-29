package com.mobydigital.votaciones.controller;

import com.mobydigital.votaciones.dto.VotoRequestDTO;
import com.mobydigital.votaciones.dto.VotoResponseDTO;
import com.mobydigital.votaciones.dto.VotosCountDTO;
import com.mobydigital.votaciones.service.VotoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar votos y estadisticas
 */
@RestController
@RequestMapping("/api/votos")
@RequiredArgsConstructor
public class VotoController {

    private final VotoService service;

    /**
     * Registra un nuevo voto
     */
    @PostMapping
    public ResponseEntity<VotoResponseDTO> registrarVoto(@Valid @RequestBody VotoRequestDTO request) {
        VotoResponseDTO response = service.registrarVoto(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Obtiene todos los votos
     */
    @GetMapping
    public ResponseEntity<List<VotoResponseDTO>> findAll() {
        List<VotoResponseDTO> votos = service.findAll();
        return ResponseEntity.ok(votos);
    }

    /**
     * Cuenta los votos de un candidato especifico
     */
    @GetMapping("/count/candidato/{candidatoId}")
    public ResponseEntity<Long> countByCandidato(@PathVariable Long candidatoId) {
        Long count = service.countByCandidato(candidatoId);
        return ResponseEntity.ok(count);
    }

    /**
     * Cuenta los votos de un partido especifico
     */
    @GetMapping("/count/partido/{partidoId}")
    public ResponseEntity<Long> countByPartido(@PathVariable Long partidoId) {
        Long count = service.countByPartido(partidoId);
        return ResponseEntity.ok(count);
    }

    /**
     * Obtiene estadisticas de votos por candidato
     */
    @GetMapping("/estadisticas/candidatos")
    public ResponseEntity<List<VotosCountDTO>> getEstadisticasPorCandidato() {
        List<VotosCountDTO> estadisticas = service.getEstadisticasPorCandidato();
        return ResponseEntity.ok(estadisticas);
    }

    /**
     * Obtiene estadisticas de votos por partido
     */
    @GetMapping("/estadisticas/partidos")
    public ResponseEntity<List<VotosCountDTO>> getEstadisticasPorPartido() {
        List<VotosCountDTO> estadisticas = service.getEstadisticasPorPartido();
        return ResponseEntity.ok(estadisticas);
    }

}
