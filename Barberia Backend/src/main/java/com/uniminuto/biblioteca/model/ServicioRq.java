package com.uniminuto.biblioteca.model; // Manteniendo el paquete base "biblioteca"

import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal; // Importar para manejar valores monetarios con precisión

/**
 * Objeto de solicitud para la creación o actualización de un servicio.
 */
@Data
@NoArgsConstructor
public class ServicioRq {
    private Integer idServicio; // Para actualizaciones
    private String nombreServicio;
    private String descripcion;
    private BigDecimal precio;
}
