package com.mobydigital.votaciones.controller;

import com.mobydigital.votaciones.dto.VotoCountResponseDTO;
import com.mobydigital.votaciones.dto.VotoRequestDTO;
import com.mobydigital.votaciones.dto.VotoResponseDTO;
import com.mobydigital.votaciones.dto.VotosCountDTO;
import com.mobydigital.votaciones.service.VotoService;
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
 * Controlador REST para gestionar votos y estadisticas
 */
@Tag(name = "Votos", description = "API para registro de votos y consultas de estadisticas")
@RestController
@RequestMapping("/api/votos")
@RequiredArgsConstructor
public class VotoController {

    private final VotoService service;

    @Operation(summary = "Registrar voto", description = "Registra un nuevo voto para un candidato")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Voto registrado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos"),
            @ApiResponse(responseCode = "404", description = "Candidato no encontrado")
    })
    @PostMapping
    public ResponseEntity<VotoResponseDTO> registrarVoto(@Valid @RequestBody VotoRequestDTO request) {
        VotoResponseDTO response = service.registrarVoto(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Listar votos", description = "Obtiene la lista de todos los votos registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    })
    @GetMapping
    public ResponseEntity<List<VotoResponseDTO>> findAll() {
        List<VotoResponseDTO> votos = service.findAll();
        return ResponseEntity.ok(votos);
    }

    @Operation(summary = "Contar votos por candidato", description = "Obtiene el numero total de votos de un candidato con informacion detallada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conteo obtenido exitosamente"),
            @ApiResponse(responseCode = "404", description = "Candidato no encontrado")
    })
    @GetMapping("/count/candidato/{candidatoId}")
    public ResponseEntity<VotoCountResponseDTO> countByCandidato(@PathVariable Long candidatoId) {
        VotoCountResponseDTO response = service.countByCandidatoDetallado(candidatoId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Contar votos por partido", description = "Obtiene el numero total de votos de un partido politico con informacion detallada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conteo obtenido exitosamente"),
            @ApiResponse(responseCode = "404", description = "Partido no encontrado")
    })
    @GetMapping("/count/partido/{partidoId}")
    public ResponseEntity<VotoCountResponseDTO> countByPartido(@PathVariable Long partidoId) {
        VotoCountResponseDTO response = service.countByPartidoDetallado(partidoId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Estadisticas por candidato", description = "Obtiene estadisticas detalladas de votos por candidato")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estadisticas obtenidas exitosamente")
    })
    @GetMapping("/estadisticas/candidatos")
    public ResponseEntity<List<VotosCountDTO>> getEstadisticasPorCandidato() {
        List<VotosCountDTO> estadisticas = service.getEstadisticasPorCandidato();
        return ResponseEntity.ok(estadisticas);
    }

    @Operation(summary = "Estadisticas por partido", description = "Obtiene estadisticas detalladas de votos por partido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estadisticas obtenidas exitosamente")
    })
    @GetMapping("/estadisticas/partidos")
    public ResponseEntity<List<VotosCountDTO>> getEstadisticasPorPartido() {
        List<VotosCountDTO> estadisticas = service.getEstadisticasPorPartido();
        return ResponseEntity.ok(estadisticas);
    }

}
