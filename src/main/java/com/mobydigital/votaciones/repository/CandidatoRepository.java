package com.mobydigital.votaciones.repository;

import com.mobydigital.votaciones.entity.Candidato;
import com.mobydigital.votaciones.entity.PartidoPolitico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para operaciones de base de datos de Candidato
 */
@Repository
public interface CandidatoRepository extends JpaRepository<Candidato, Long> {

    /**
     * Busca todos los candidatos de un partido especifico por ID
     */
    List<Candidato> findByPartidoId(Long partidoId);

    /**
     * Busca todos los candidatos de un partido especifico
     */
    List<Candidato> findByPartido(PartidoPolitico partido);

}
