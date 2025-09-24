package com.uniminuto.biblioteca.services; // Manteniendo el paquete base "biblioteca"

import com.uniminuto.biblioteca.entity.Pago;
import com.uniminuto.biblioteca.model.PagoRq;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import java.time.LocalDate;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 * Interfaz que define los servicios de negocio para la gestión de pagos.
 */
public interface PagoService {

    /**
     * Obtiene el listado de todos los pagos.
     * @return Lista de pagos.
     */
    List<Pago> listarPagos();

    /**
     * Obtiene un pago por su ID.
     * @param idPago ID del pago.
     * @return El objeto Pago.
     * @throws BadRequestException Si el pago no se encuentra.
     */
    Pago obtenerPagoPorId(Integer idPago) throws BadRequestException;

    /**
     * Crea un nuevo pago.
     * @param pagoRq Objeto de solicitud con los datos del pago.
     * @return Respuesta genérica de éxito o error.
     * @throws BadRequestException Si hay un error de validación.
     */
    RespuestaGenericaRs crearPago(PagoRq pagoRq) throws BadRequestException;

    /**
     * Actualiza un pago existente.
     * @param pagoRq Objeto de solicitud con los datos actualizados del pago.
     * @return Respuesta genérica de éxito o error.
     * @throws BadRequestException Si hay un error de validación o el pago no existe.
     */
    RespuestaGenericaRs actualizarPago(PagoRq pagoRq) throws BadRequestException;

    /**
     * Elimina un pago por su ID.
     * @param idPago ID del pago a eliminar.
     * @return Respuesta genérica de éxito o error.
     * @throws BadRequestException Si el pago no existe o no se puede eliminar.
     */
    RespuestaGenericaRs eliminarPago(Integer idPago) throws BadRequestException;

    /**
     * Lista pagos por el ID de la cita asociada.
     * @param idCita ID de la cita.
     * @return Lista de pagos para la cita especificada.
     * @throws BadRequestException Si la cita no existe o hay un error.
     */
    List<Pago> listarPagosPorCita(Integer idCita) throws BadRequestException;

    /**
     * Lista pagos por método de pago.
     * @param metodoPago Método de pago.
     * @return Lista de pagos.
     * @throws BadRequestException Si hay un error.
     */
    List<Pago> listarPagosPorMetodo(Pago.MetodoPago metodoPago) throws BadRequestException;

    /**
     * Lista pagos dentro de un rango de fechas.
     * @param fechaInicio Fecha de inicio del rango.
     * @param fechaFin Fecha de fin del rango.
     * @return Lista de pagos.
     * @throws BadRequestException Si hay un error en las fechas.
     */
    List<Pago> listarPagosPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) throws BadRequestException;
}
