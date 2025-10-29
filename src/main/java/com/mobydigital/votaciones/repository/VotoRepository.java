package com.mobydigital.votaciones.repository;

import com.mobydigital.votaciones.entity.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
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

}
