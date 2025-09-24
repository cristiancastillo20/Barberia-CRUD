package com.uniminuto.biblioteca.services; // Manteniendo el paquete base "biblioteca"

import com.uniminuto.biblioteca.entity.Sede;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.model.SedeRq;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 * Interfaz que define los servicios de negocio para la gestión de sedes.
 */
public interface SedeService {

    /**
     * Obtiene el listado de todas las sedes.
     * @return Lista de sedes.
     */
    List<Sede> listarSedes();

    /**
     * Obtiene una sede por su ID.
     * @param idSede ID de la sede.
     * @return El objeto Sede.
     * @throws BadRequestException Si la sede no se encuentra.
     */
    Sede obtenerSedePorId(Integer idSede) throws BadRequestException;

    /**
     * Crea una nueva sede.
     * @param sedeRq Objeto de solicitud con los datos de la sede.
     * @return Respuesta genérica de éxito o error.
     * @throws BadRequestException Si hay un error de validación o duplicidad.
     */
    RespuestaGenericaRs crearSede(SedeRq sedeRq) throws BadRequestException;

    /**
     * Actualiza una sede existente.
     * @param sedeRq Objeto de solicitud con los datos actualizados de la sede.
     * @return Respuesta genérica de éxito o error.
     * @throws BadRequestException Si hay un error de validación o la sede no existe.
     */
    RespuestaGenericaRs actualizarSede(SedeRq sedeRq) throws BadRequestException;

    /**
     * Elimina una sede por su ID.
     * @param idSede ID de la sede a eliminar.
     * @return Respuesta genérica de éxito o error.
     * @throws BadRequestException Si la sede no existe o no se puede eliminar.
     */
    RespuestaGenericaRs eliminarSede(Integer idSede) throws BadRequestException;
}
