package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Barbero;
import com.uniminuto.biblioteca.model.BarberoRq;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 * Interfaz que define los servicios de negocio para la gestión de barberos.
 */
public interface BarberoService {

    /**
     * Obtiene el listado de todos los barberos.
     * @return Lista de barberos.
     */
    List<Barbero> listarBarberos();

    /**
     * Obtiene el listado de barberos por el ID de la sede.
     * @param idSede ID de la sede.
     * @return Lista de barberos de la sede especificada.
     * @throws BadRequestException Si la sede no existe o hay un error.
     */
    List<Barbero> listarBarberosPorSede(Integer idSede) throws BadRequestException;

    /**
     * Obtiene un barbero por su ID.
     * @param idBarbero ID del barbero.
     * @return El objeto Barbero.
     * @throws BadRequestException Si el barbero no se encuentra.
     */
    Barbero obtenerBarberoPorId(Integer idBarbero) throws BadRequestException;

    /**
     * Crea un nuevo barbero.
     * @param barberoRq Objeto de solicitud con los datos del barbero.
     * @return Respuesta genérica de éxito o error.
     * @throws BadRequestException Si hay un error de validación o duplicidad.
     */
    RespuestaGenericaRs crearBarbero(BarberoRq barberoRq) throws BadRequestException;

    /**
     * Actualiza un barbero existente.
     * @param barberoRq Objeto de solicitud con los datos actualizados del barbero.
     * @return Respuesta genérica de éxito o error.
     * @throws BadRequestException Si hay un error de validación o el barbero no existe.
     */
    RespuestaGenericaRs actualizarBarbero(BarberoRq barberoRq) throws BadRequestException;

    /**
     * Elimina un barbero por su ID.
     * @param idBarbero ID del barbero a eliminar.
     * @return Respuesta genérica de éxito o error.
     * @throws BadRequestException Si el barbero no existe o no se puede eliminar.
     */
    RespuestaGenericaRs eliminarBarbero(Integer idBarbero) throws BadRequestException;
}
