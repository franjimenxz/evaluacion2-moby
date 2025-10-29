package com.mobydigital.votaciones.controller;

import com.mobydigital.votaciones.dto.PartidoPoliticoRequestDTO;
import com.mobydigital.votaciones.dto.PartidoPoliticoResponseDTO;
import com.mobydigital.votaciones.service.PartidoPoliticoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar partidos politicos
 */
@RestController
@RequestMapping("/api/partidos")
@RequiredArgsConstructor
public class PartidoPoliticoController {

    private final PartidoPoliticoService service;

    /**
     * Crea un nuevo partido politico
     */
    @PostMapping
    public ResponseEntity<PartidoPoliticoResponseDTO> create(@Valid @RequestBody PartidoPoliticoRequestDTO request) {
        PartidoPoliticoResponseDTO response = service.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Obtiene todos los partidos politicos
     */
    @GetMapping
    public ResponseEntity<List<PartidoPoliticoResponseDTO>> findAll() {
        List<PartidoPoliticoResponseDTO> partidos = service.findAll();
        return ResponseEntity.ok(partidos);
    }

    /**
     * Busca un partido politico por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<PartidoPoliticoResponseDTO> findById(@PathVariable Long id) {
        PartidoPoliticoResponseDTO partido = service.findById(id);
        return ResponseEntity.ok(partido);
    }

    /**
     * Elimina un partido politico por ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
