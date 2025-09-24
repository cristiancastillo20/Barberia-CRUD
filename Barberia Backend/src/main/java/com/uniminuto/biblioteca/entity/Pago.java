package com.uniminuto.biblioteca.entity; // Manteniendo el paquete base "biblioteca"

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa la tabla "Pago" en la base de datos de la barbería.
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "Pago") // Nombre de la tabla en la DB
public class Pago implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador único del pago.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private Integer idPago;

    /**
     * Monto del pago.
     */
    @Column(name = "monto", nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    /**
     * Fecha en que se realizó el pago.
     */
    @Column(name = "fecha_pago", nullable = false)
    private LocalDate fechaPago;

    /**
     * Método de pago (Efectivo, Tarjeta, Transferencia).
     */
    @Enumerated(EnumType.STRING) // Almacena el nombre del enum como String en la DB
    @Column(name = "metodo_pago", nullable = false, length = 50)
    private MetodoPago metodoPago;

    /**
     * Cita asociada al pago.
     */
    @ManyToOne
    @JoinColumn(name = "id_cita", nullable = false)
    private Cita cita;

    // Enum para los métodos de pago
    public enum MetodoPago {
        Efectivo, Tarjeta, Transferencia
    }

    // Constructor con campos básicos para facilitar la creación si es necesario
    public Pago(Integer idPago, BigDecimal monto, LocalDate fechaPago, MetodoPago metodoPago, Cita cita) {
        this.idPago = idPago;
        this.monto = monto;
        this.fechaPago = fechaPago;
        this.metodoPago = metodoPago;
        this.cita = cita;
    }
}
