package com.mobydigital.votaciones.exception;

/**
 * Excepcion lanzada cuando no se encuentra un recurso en la base de datos
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String recurso, String campo, Object valor) {
        super(String.format("%s no encontrado con %s: %s", recurso, campo, valor));
    }

}
