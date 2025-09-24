package com.uniminuto.biblioteca.entity; // Manteniendo el paquete base "biblioteca"

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa la tabla "Sede" en la base de datos de la barbería.
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "Sede") // Nombre de la tabla en la DB
public class Sede implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador único de la sede.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sede")
    private Integer idSede;

    /**
     * Nombre de la sede.
     */
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    /**
     * Dirección de la sede.
     */
    @Column(name = "direccion", nullable = false, length = 255)
    private String direccion;

    /**
     * Teléfono de la sede.
     */
    @Column(name = "telefono", nullable = false, length = 15)
    private String telefono;

    // Constructor con campos básicos para facilitar la creación si es necesario
    public Sede(Integer idSede, String nombre, String direccion, String telefono) {
        this.idSede = idSede;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
    }
}
