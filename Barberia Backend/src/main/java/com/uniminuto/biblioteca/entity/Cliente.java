package com.uniminuto.biblioteca.entity; // Manteniendo el paquete base "biblioteca"

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa la tabla "Cliente" en la base de datos de la barbería.
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "Cliente") // Nombre de la tabla en la DB
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador único del cliente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Integer idCliente;

    /**
     * Nombre del cliente.
     */
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    /**
     * Apellido del cliente.
     */
    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido;

    /**
     * Teléfono del cliente.
     */
    @Column(name = "telefono", nullable = false, length = 15)
    private String telefono;

    /**
     * Correo electrónico del cliente.
     */
    @Column(name = "correo", length = 100)
    private String correo;

    /**
     * Fecha de registro del cliente.
     */
    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro;

    // Constructor con campos básicos para facilitar la creación si es necesario
    public Cliente(Integer idCliente, String nombre, String apellido, String telefono, String correo, LocalDate fechaRegistro) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.correo = correo;
        this.fechaRegistro = fechaRegistro;
    }
}
