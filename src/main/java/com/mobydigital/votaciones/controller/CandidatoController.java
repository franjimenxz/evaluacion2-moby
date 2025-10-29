package com.mobydigital.votaciones.controller;

import com.mobydigital.votaciones.dto.CandidatoRequestDTO;
import com.mobydigital.votaciones.dto.CandidatoResponseDTO;
import com.mobydigital.votaciones.service.CandidatoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar candidatos
 */
@RestController
@RequestMapping("/api/candidatos")
@RequiredArgsConstructor
public class CandidatoController {

    private final CandidatoService service;

    /**
     * Crea un nuevo candidato
     */
    @PostMapping
    public ResponseEntity<CandidatoResponseDTO> create(@Valid @RequestBody CandidatoRequestDTO request) {
        CandidatoResponseDTO response = service.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Obtiene todos los candidatos
     */
    @GetMapping
    public ResponseEntity<List<CandidatoResponseDTO>> findAll() {
        List<CandidatoResponseDTO> candidatos = service.findAll();
        return ResponseEntity.ok(candidatos);
    }

    /**
     * Busca un candidato por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<CandidatoResponseDTO> findById(@PathVariable Long id) {
        CandidatoResponseDTO candidato = service.findById(id);
        return ResponseEntity.ok(candidato);
    }

    /**
     * Elimina un candidato por ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Busca candidatos por partido politico
     */
    @GetMapping("/partido/{partidoId}")
    public ResponseEntity<List<CandidatoResponseDTO>> findByPartido(@PathVariable Long partidoId) {
        List<CandidatoResponseDTO> candidatos = service.findByPartido(partidoId);
        return ResponseEntity.ok(candidatos);
    }

}