package com.uniminuto.biblioteca.model; // Manteniendo el paquete base "biblioteca"

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Objeto de solicitud para la creación o actualización de una sede.
 */
@Data
@NoArgsConstructor
public class SedeRq {
    private Integer idSede; // Para actualizaciones
    private String nombre;
    private String direccion;
    private String telefono;
}
