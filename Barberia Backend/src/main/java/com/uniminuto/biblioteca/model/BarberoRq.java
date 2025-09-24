package com.uniminuto.biblioteca.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Objeto de solicitud para la creación o actualización de un barbero.
 */
@Data
@NoArgsConstructor
public class BarberoRq {
    private Integer idBarbero; // Para actualizaciones
    private String nombre;
    private String apellido;
    private String especialidad;
    private Integer idSede; // ID de la sede a la que pertenece
}
