package com.mobydigital.votaciones.repository;

import com.mobydigital.votaciones.entity.PartidoPolitico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para operaciones de base de datos de PartidoPolitico
 */
@Repository
public interface PartidoPoliticoRepository extends JpaRepository<PartidoPolitico, Long> {

    /**
     * Busca un partido politico por su sigla
     */
    Optional<PartidoPolitico> findBySigla(String sigla);

    /**
     * Verifica si existe un partido con la sigla especificada
     */
    boolean existsBySigla(String sigla);

}
