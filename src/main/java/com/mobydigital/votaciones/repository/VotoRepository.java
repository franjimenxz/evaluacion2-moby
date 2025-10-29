package com.mobydigital.votaciones.repository;

import com.mobydigital.votaciones.entity.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para operaciones de base de datos de Voto
 */
@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {

    /**
     * Cuenta la cantidad de votos de un candidato especifico
     */
    Long countByCandidatoId(Long candidatoId);

    /**
     * Busca todos los votos de un candidato especifico
     */
    List<Voto> findByCandidatoId(Long candidatoId);

    /**
     * Cuenta los votos agrupados por partido politico
     * Retorna una lista de arrays donde [0] es el partido y [1] es el conteo
     */
    @Query("SELECT v.candidato.partido, COUNT(v) FROM Voto v GROUP BY v.candidato.partido")
    List<Object[]> countVotosByPartido();

    /**
     * Cuenta los votos de un partido especifico por su ID
     */
    @Query("SELECT COUNT(v) FROM Voto v WHERE v.candidato.partido.id = :partidoId")
    Long countByPartidoId(@Param("partidoId") Long partidoId);

}
