package com.uniminuto.biblioteca.services; // Manteniendo el paquete base "biblioteca"

import com.uniminuto.biblioteca.entity.Cita;
import com.uniminuto.biblioteca.model.CitaRq;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import java.time.LocalDate;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 * Interfaz que define los servicios de negocio para la gestión de citas.
 */
public interface CitaService {

    /**
     * Obtiene el listado de todas las citas.
     * @return Lista de citas.
     */
    List<Cita> listarCitas();

    /**
     * Obtiene una cita por su ID.
     * @param idCita ID de la cita.
     * @return El objeto Cita.
     * @throws BadRequestException Si la cita no se encuentra.
     */
    Cita obtenerCitaPorId(Integer idCita) throws BadRequestException;

    /**
     * Crea una nueva cita.
     * @param citaRq Objeto de solicitud con los datos de la cita.
     * @return Respuesta genérica de éxito o error.
     * @throws BadRequestException Si hay un error de validación o conflicto de horario.
     */
    RespuestaGenericaRs crearCita(CitaRq citaRq) throws BadRequestException;

    /**
     * Actualiza una cita existente.
     * @param citaRq Objeto de solicitud con los datos actualizados de la cita.
     * @return Respuesta genérica de éxito o error.
     * @throws BadRequestException Si hay un error de validación o la cita no existe.
     */
    RespuestaGenericaRs actualizarCita(CitaRq citaRq) throws BadRequestException;

    /**
     * Elimina una cita por su ID.
     * @param idCita ID de la cita a eliminar.
     * @return Respuesta genérica de éxito o error.
     * @throws BadRequestException Si la cita no existe o no se puede eliminar.
     */
    RespuestaGenericaRs eliminarCita(Integer idCita) throws BadRequestException;

    /**
     * Lista citas por fecha y barbero.
     * @param fecha Fecha de las citas.
     * @param idBarbero ID del barbero.
     * @return Lista de citas.
     * @throws BadRequestException Si hay un error o no se encuentran citas.
     */
    List<Cita> listarCitasPorFechaYBarbero(LocalDate fecha, Integer idBarbero) throws BadRequestException;

    /**
     * Lista citas por cliente.
     * @param idCliente ID del cliente.
     * @return Lista de citas del cliente.
     * @throws BadRequestException Si hay un error o no se encuentran citas.
     */
    List<Cita> listarCitasPorCliente(Integer idCliente) throws BadRequestException;
}
