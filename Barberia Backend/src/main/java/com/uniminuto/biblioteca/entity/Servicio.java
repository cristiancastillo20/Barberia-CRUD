package com.uniminuto.biblioteca.entity; // Manteniendo el paquete base "biblioteca"

import java.io.Serializable;
import java.math.BigDecimal; // Importar para manejar valores monetarios con precisión
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa la tabla "Servicio" en la base de datos de la barbería.
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "Servicio") // Nombre de la tabla en la DB
public class Servicio implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador único del servicio.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicio")
    private Integer idServicio;

    /**
     * Nombre del servicio (ej. "Corte Caballero", "Arreglo de Barba").
     */
    @Column(name = "nombre_servicio", nullable = false, length = 100)
    private String nombreServicio;

    /**
     * Descripción detallada del servicio.
     */
    @Column(name = "descripcion", columnDefinition = "TEXT") // Mapea a tipo TEXT en DB
    private String descripcion;

    /**
     * Precio del servicio. Se usa BigDecimal para precisión monetaria.
     */
    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    // Constructor con campos básicos para facilitar la creación si es necesario
    public Servicio(Integer idServicio, String nombreServicio, String descripcion, BigDecimal precio) {
        this.idServicio = idServicio;
        this.nombreServicio = nombreServicio;
        this.descripcion = descripcion;
        this.precio = precio;
    }
}
