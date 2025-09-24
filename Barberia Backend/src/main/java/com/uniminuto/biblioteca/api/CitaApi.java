package com.uniminuto.biblioteca.api; // Manteniendo el paquete base "biblioteca"

import com.uniminuto.biblioteca.entity.Cita;
import com.uniminuto.biblioteca.model.CitaRq;
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
 * Interfaz que define los endpoints para la gestión de citas.
 */
@CrossOrigin(origins = "*") // Permite solicitudes desde cualquier origen (para desarrollo)
@RequestMapping("/cita") // El context-path '/biblioteca/v1' se añade globalmente en application.properties
public interface CitaApi {

    /**
     * Lista todas las citas registradas.
     * @return Lista de citas.
     * @throws BadRequestException Excepción si hay un error.
     */
    @GetMapping("/listar")
    ResponseEntity<List<Cita>> listarCitas() throws BadRequestException;

    /**
     * Obtiene una cita por su ID.
     * @param idCita ID de la cita.
     * @return La cita encontrada.
     * @throws BadRequestException Excepción si la cita no existe.
     */
    @GetMapping("/obtener-por-id/{idCita}")
    ResponseEntity<Cita> obtenerCitaPorId(@PathVariable Integer idCita) throws BadRequestException;

    /**
     * Crea una nueva cita.
     * @param citaRq Datos de la cita a crear.
     * @return Respuesta genérica de éxito o error.
     * @throws BadRequestException Excepción si hay errores de validación o conflicto de horario.
     */
    @PostMapping("/crear")
    ResponseEntity<RespuestaGenericaRs> crearCita(@RequestBody CitaRq citaRq) throws BadRequestException;

    /**
     * Actualiza una cita existente.
     * @param citaRq Datos de la cita a actualizar (debe incluir el ID).
     * @return Respuesta genérica de éxito o error.
     * @throws BadRequestException Excepción si hay errores de validación o la cita no existe.
     */
    @PutMapping("/actualizar")
    ResponseEntity<RespuestaGenericaRs> actualizarCita(@RequestBody CitaRq citaRq) throws BadRequestException;

    /**
     * Elimina una cita por su ID.
     * @param idCita ID de la cita a eliminar.
     * @return Respuesta genérica de éxito o error.
     * @throws BadRequestException Excepción si la cita no existe o no se puede eliminar.
     */
    @DeleteMapping("/eliminar/{idCita}")
    ResponseEntity<RespuestaGenericaRs> eliminarCita(@PathVariable Integer idCita) throws BadRequestException;

    /**
     * Lista citas por fecha y barbero.
     * @param fecha Fecha de las citas (formato YYYY-MM-DD).
     * @param idBarbero ID del barbero.
     * @return Lista de citas.
     * @throws BadRequestException Si hay un error o no se encuentran citas.
     */
    @GetMapping("/listar-por-fecha-barbero")
    ResponseEntity<List<Cita>> listarCitasPorFechaYBarbero(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam Integer idBarbero) throws BadRequestException;

    /**
     * Lista citas por cliente.
     * @param idCliente ID del cliente.
     * @return Lista de citas del cliente.
     * @throws BadRequestException Si hay un error o no se encuentran citas.
     */
    @GetMapping("/listar-por-cliente/{idCliente}")
    ResponseEntity<List<Cita>> listarCitasPorCliente(@PathVariable Integer idCliente) throws BadRequestException;
}
