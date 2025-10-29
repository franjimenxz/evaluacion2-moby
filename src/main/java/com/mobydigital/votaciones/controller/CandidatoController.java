package com.mobydigital.votaciones.controller;

import com.mobydigital.votaciones.dto.CandidatoRequestDTO;
import com.mobydigital.votaciones.dto.CandidatoResponseDTO;
import com.mobydigital.votaciones.service.CandidatoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar candidatos
 */
@Tag(name = "Candidatos", description = "API para gestion de candidatos")
@RestController
@RequestMapping("/api/candidatos")
@RequiredArgsConstructor
public class CandidatoController {

    private final CandidatoService service;

    @Operation(summary = "Crear candidato", description = "Crea un nuevo candidato asociado a un partido politico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Candidato creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos"),
            @ApiResponse(responseCode = "404", description = "Partido politico no encontrado")
    })
    @PostMapping
    public ResponseEntity<CandidatoResponseDTO> create(@Valid @RequestBody CandidatoRequestDTO request) {
        CandidatoResponseDTO response = service.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Listar candidatos", description = "Obtiene la lista de todos los candidatos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    })
    @GetMapping
    public ResponseEntity<List<CandidatoResponseDTO>> findAll() {
        List<CandidatoResponseDTO> candidatos = service.findAll();
        return ResponseEntity.ok(candidatos);
    }

    @Operation(summary = "Buscar candidato por ID", description = "Obtiene un candidato especifico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Candidato encontrado"),
            @ApiResponse(responseCode = "404", description = "Candidato no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CandidatoResponseDTO> findById(@PathVariable Long id) {
        CandidatoResponseDTO candidato = service.findById(id);
        return ResponseEntity.ok(candidato);
    }

    @Operation(summary = "Eliminar candidato", description = "Elimina un candidato del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Candidato eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Candidato no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar candidatos por partido", description = "Obtiene todos los candidatos de un partido politico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
            @ApiResponse(responseCode = "404", description = "Partido no encontrado")
    })
    @GetMapping("/partido/{partidoId}")
    public ResponseEntity<List<CandidatoResponseDTO>> findByPartido(@PathVariable Long partidoId) {
        List<CandidatoResponseDTO> candidatos = service.findByPartido(partidoId);
        return ResponseEntity.ok(candidatos);
    }

}