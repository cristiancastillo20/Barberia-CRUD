package com.uniminuto.biblioteca.model; // Manteniendo el paquete base "biblioteca"

import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import com.uniminuto.biblioteca.entity.Pago.MetodoPago; // Importar el enum MetodoPago

/**
 * Objeto de solicitud para la creación o actualización de un pago.
 */
@Data
@NoArgsConstructor
public class PagoRq {
    private Integer idPago; // Para actualizaciones
    private BigDecimal monto;
    private LocalDate fechaPago;
    private MetodoPago metodoPago; // Usar el enum directamente
    private Integer idCita;
}
