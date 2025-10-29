package com.mobydigital.votaciones.exception;

/**
 * Excepcion lanzada cuando hay un error en la logica de negocio o validacion
 */
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }

}
