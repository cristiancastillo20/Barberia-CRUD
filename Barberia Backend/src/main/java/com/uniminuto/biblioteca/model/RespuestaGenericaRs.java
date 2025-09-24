package com.uniminuto.biblioteca.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Objeto de respuesta genérica para operaciones del API.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaGenericaRs {
    private boolean success;
    private String message;

    public RespuestaGenericaRs(String message) {
        this.success = true;
        this.message = message;
    }
}
