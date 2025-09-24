package com.uniminuto.biblioteca.api; // Manteniendo el paquete base "biblioteca"

import com.uniminuto.biblioteca.entity.Sede;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.model.SedeRq;

import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam; // No usado en SedeApi por ahora, pero se mantiene si se necesita

/**
 * Interfaz que define los endpoints para la gestión de sedes.
 */
@CrossOrigin(origins = "*") // Permite solicitudes desde cualquier origen (para desarrollo)
@RequestMapping("/sede")
public interface SedeApi {

    /**
     * Lista todas las sedes registradas.
     * @return Lista de sedes.
     * @throws BadRequestException Excepción si hay un error.
     */
    @GetMapping("/listar")
    ResponseEntity<List<Sede>> listarSedes() throws BadRequestException;

    /**
     * Obtiene una sede por su ID.
     * @param idSede ID de la sede.
     * @return La sede encontrada.
     * @throws BadRequestException Excepción si la sede no existe.
     */
    @GetMapping("/obtener-por-id/{idSede}")
    ResponseEntity<Sede> obtenerSedePorId(@PathVariable Integer idSede) throws BadRequestException;

    /**
     * Crea una nueva sede.
     * @param sedeRq Datos de la sede a crear.
     * @return Respuesta genérica de éxito o error.
     * @throws BadRequestException Excepción si hay errores de validación o duplicidad.
     */
    @PostMapping("/crear")
    ResponseEntity<RespuestaGenericaRs> crearSede(@RequestBody SedeRq sedeRq) throws BadRequestException;

    /**
     * Actualiza una sede existente.
     * @param sedeRq Datos de la sede a actualizar (debe incluir el ID).
     * @return Respuesta genérica de éxito o error.
     * @throws BadRequestException Excepción si hay errores de validación o la sede no existe.
     */
    @PutMapping("/actualizar")
    ResponseEntity<RespuestaGenericaRs> actualizarSede(@RequestBody SedeRq sedeRq) throws BadRequestException;

    /**
     * Elimina una sede por su ID.
     * @param idSede ID de la sede a eliminar.
     * @return Respuesta genérica de éxito o error.
     * @throws BadRequestException Excepción si la sede no existe o no se puede eliminar.
     */
    @DeleteMapping("/eliminar/{idSede}")
    ResponseEntity<RespuestaGenericaRs> eliminarSede(@PathVariable Integer idSede) throws BadRequestException;
}
