package com.mobydigital.votaciones.controller;

import com.mobydigital.votaciones.dto.PartidoPoliticoRequestDTO;
import com.mobydigital.votaciones.dto.PartidoPoliticoResponseDTO;
import com.mobydigital.votaciones.service.PartidoPoliticoService;
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
 * Controlador REST para gestionar partidos politicos
 */
@Tag(name = "Partidos Politicos", description = "API para gestion de partidos politicos")
@RestController
@RequestMapping("/api/partidos")
@RequiredArgsConstructor
public class PartidoPoliticoController {

    private final PartidoPoliticoService service;

    @Operation(summary = "Crear partido politico", description = "Crea un nuevo partido politico en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Partido creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos o sigla duplicada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<PartidoPoliticoResponseDTO> create(@Valid @RequestBody PartidoPoliticoRequestDTO request) {
        PartidoPoliticoResponseDTO response = service.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Listar partidos", description = "Obtiene la lista de todos los partidos politicos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    })
    @GetMapping
    public ResponseEntity<List<PartidoPoliticoResponseDTO>> findAll() {
        List<PartidoPoliticoResponseDTO> partidos = service.findAll();
        return ResponseEntity.ok(partidos);
    }

    @Operation(summary = "Buscar partido por ID", description = "Obtiene un partido politico especifico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Partido encontrado"),
            @ApiResponse(responseCode = "404", description = "Partido no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PartidoPoliticoResponseDTO> findById(@PathVariable Long id) {
        PartidoPoliticoResponseDTO partido = service.findById(id);
        return ResponseEntity.ok(partido);
    }

    @Operation(summary = "Eliminar partido", description = "Elimina un partido politico del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Partido eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Partido no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
