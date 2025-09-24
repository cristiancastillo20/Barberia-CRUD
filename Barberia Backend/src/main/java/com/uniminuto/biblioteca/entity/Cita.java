package com.uniminuto.biblioteca.entity; // Manteniendo el paquete base "biblioteca"

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
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
 * Entidad que representa la tabla "Cita" en la base de datos de la barbería.
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "Cita") // Nombre de la tabla en la DB
public class Cita implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador único de la cita.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cita")
    private Integer idCita;

    /**
     * Fecha de la cita.
     */
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    /**
     * Hora de la cita.
     */
    @Column(name = "hora", nullable = false)
    private LocalTime hora;

    /**
     * Cliente asociado a la cita.
     */
    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    /**
     * Barbero asociado a la cita.
     */
    @ManyToOne
    @JoinColumn(name = "id_barbero", nullable = false)
    private Barbero barbero;

    /**
     * Servicio asociado a la cita.
     */
    @ManyToOne
    @JoinColumn(name = "id_servicio", nullable = false)
    private Servicio servicio;

    // Constructor con campos básicos para facilitar la creación si es necesario
    public Cita(Integer idCita, LocalDate fecha, LocalTime hora, Cliente cliente, Barbero barbero, Servicio servicio) {
        this.idCita = idCita;
        this.fecha = fecha;
        this.hora = hora;
        this.cliente = cliente;
        this.barbero = barbero;
        this.servicio = servicio;
    }
}
