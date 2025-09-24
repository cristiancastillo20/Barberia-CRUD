package com.uniminuto.biblioteca.services; // Manteniendo el paquete base "biblioteca"

import com.uniminuto.biblioteca.entity.Servicio;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.model.ServicioRq;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 * Interfaz que define los servicios de negocio para la gestión de servicios.
 */
public interface ServicioService {

    /**
     * Obtiene el listado de todos los servicios.
     * @return Lista de servicios.
     */
    List<Servicio> listarServicios();

    /**
     * Obtiene un servicio por su ID.
     * @param idServicio ID del servicio.
     * @return El objeto Servicio.
     * @throws BadRequestException Si el servicio no se encuentra.
     */
    Servicio obtenerServicioPorId(Integer idServicio) throws BadRequestException;

    /**
     * Crea un nuevo servicio.
     * @param servicioRq Objeto de solicitud con los datos del servicio.
     * @return Respuesta genérica de éxito o error.
     * @throws BadRequestException Si hay un error de validación o duplicidad.
     */
    RespuestaGenericaRs crearServicio(ServicioRq servicioRq) throws BadRequestException;

    /**
     * Actualiza un servicio existente.
     * @param servicioRq Objeto de solicitud con los datos actualizados del servicio.
     * @return Respuesta genérica de éxito o error.
     * @throws BadRequestException Si hay un error de validación o el servicio no existe.
     */
    RespuestaGenericaRs actualizarServicio(ServicioRq servicioRq) throws BadRequestException;

    /**
     * Elimina un servicio por su ID.
     * @param idServicio ID del servicio a eliminar.
     * @return Respuesta genérica de éxito o error.
     * @throws BadRequestException Si el servicio no existe o no se puede eliminar.
     */
    RespuestaGenericaRs eliminarServicio(Integer idServicio) throws BadRequestException;
}
