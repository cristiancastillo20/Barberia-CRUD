package com.uniminuto.biblioteca.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa la tabla "Barbero" en la base de datos.
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "Barbero")
public class Barbero implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador único del barbero.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_barbero")
    private Integer idBarbero;

    /**
     * Nombre del barbero.
     */
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    /**
     * Apellido del barbero.
     */
    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido;

    /**
     * Especialidad del barbero.
     */
    @Column(name = "especialidad", length = 255)
    private String especialidad;

    /**
     * Sede a la que pertenece el barbero.
     * Se mapea la relación ManyToOne con la entidad Sede.
     */
    @ManyToOne
    @JoinColumn(name = "id_sede", nullable = false)
    private Sede sede;

    // Constructor con campos básicos para facilitar la creación si es necesario
    public Barbero(Integer idBarbero, String nombre, String apellido, String especialidad, Sede sede) {
        this.idBarbero = idBarbero;
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidad = especialidad;
        this.sede = sede;
    }
}
