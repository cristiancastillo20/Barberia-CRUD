package com.uniminuto.biblioteca.model; // Manteniendo el paquete base "biblioteca"

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate; // Para manejar la fecha de registro si se envía

/**
 * Objeto de solicitud para la creación o actualización de un cliente.
 */
@Data
@NoArgsConstructor
public class ClienteRq {
    private Integer idCliente; // Para actualizaciones
    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;
    private LocalDate fechaRegistro; // Opcional para creación, se puede omitir y usar DEFAULT (CURDATE())
}
