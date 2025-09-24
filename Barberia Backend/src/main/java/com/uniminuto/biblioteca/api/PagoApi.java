package com.uniminuto.biblioteca.api; // Manteniendo el paquete base "biblioteca"

import com.uniminuto.biblioteca.entity.Pago;
import com.uniminuto.biblioteca.model.PagoRq;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;

import java.time.LocalDate;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Interfaz que define los endpoints para la gestión de pagos.
 */
@CrossOrigin(origins = "*") // Permite solicitudes desde cualquier origen (para desarrollo)
@RequestMapping("/pago") // El context-path '/biblioteca/v1' se añade globalmente en application.properties
public interface PagoApi {

    /**
     * Lista todos los pagos registrados.
     * @return Lista de pagos.
     * @throws BadRequestException Excepción si hay un error.
     */
    @GetMapping("/listar")
    ResponseEntity<List<Pago>> listarPagos() throws BadRequestException;

    /**
     * Obtiene un pago por su ID.
     * @param idPago ID del pago.
     * @return El pago encontrado.
     * @throws BadRequestException Excepción si el pago no existe.
     */
    @GetMapping("/obtener-por-id/{idPago}")
    ResponseEntity<Pago> obtenerPagoPorId(@PathVariable Integer idPago) throws BadRequestException;

    /**
     * Crea un nuevo pago.
     * @param pagoRq Datos del pago a crear.
     * @return Respuesta genérica de éxito o error.
     * @throws BadRequestException Excepción si hay errores de validación.
     */
    @PostMapping("/crear")
    ResponseEntity<RespuestaGenericaRs> crearPago(@RequestBody PagoRq pagoRq) throws BadRequestException;

    /**
     * Actualiza un pago existente.
     * @param pagoRq Datos del pago a actualizar (debe incluir el ID).
     * @return Respuesta genérica de éxito o error.
     * @throws BadRequestException Excepción si hay errores de validación o el pago no existe.
     */
    @PutMapping("/actualizar")
    ResponseEntity<RespuestaGenericaRs> actualizarPago(@RequestBody PagoRq pagoRq) throws BadRequestException;

    /**
     * Elimina un pago por su ID.
     * @param idPago ID del pago a eliminar.
     * @return Respuesta genérica de éxito o error.
     * @throws BadRequestException Excepción si el pago no existe o no se puede eliminar.
     */
    @DeleteMapping("/eliminar/{idPago}")
    ResponseEntity<RespuestaGenericaRs> eliminarPago(@PathVariable Integer idPago) throws BadRequestException;

    /**
     * Lista pagos por el ID de la cita asociada.
     * @param idCita ID de la cita.
     * @return Lista de pagos para la cita especificada.
     * @throws BadRequestException Si la cita no existe o hay un error.
     */
    @GetMapping("/listar-por-cita/{idCita}")
    ResponseEntity<List<Pago>> listarPagosPorCita(@PathVariable Integer idCita) throws BadRequestException;

    /**
     * Lista pagos por método de pago.
     * @param metodoPago Método de pago (Efectivo, Tarjeta, Transferencia).
     * @return Lista de pagos.
     * @throws BadRequestException Si hay un error.
     */
    @GetMapping("/listar-por-metodo")
    ResponseEntity<List<Pago>> listarPagosPorMetodo(@RequestParam Pago.MetodoPago metodoPago) throws BadRequestException;

    /**
     * Lista pagos dentro de un rango de fechas.
     * @param fechaInicio Fecha de inicio del rango (formato YYYY-MM-DD).
     * @param fechaFin Fecha de fin del rango (formato YYYY-MM-DD).
     * @return Lista de pagos.
     * @throws BadRequestException Si hay un error en las fechas.
     */
    @GetMapping("/listar-por-rango-fechas")
    ResponseEntity<List<Pago>> listarPagosPorRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) throws BadRequestException;
}
