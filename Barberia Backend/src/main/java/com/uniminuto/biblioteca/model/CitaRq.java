package com.uniminuto.biblioteca.model; // Manteniendo el paquete base "biblioteca"

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Objeto de solicitud para la creación o actualización de una cita.
 */
@Data
@NoArgsConstructor
public class CitaRq {
    private Integer idCita; // Para actualizaciones
    private LocalDate fecha;
    private LocalTime hora;
    private Integer idCliente;
    private Integer idBarbero;
    private Integer idServicio;
}
