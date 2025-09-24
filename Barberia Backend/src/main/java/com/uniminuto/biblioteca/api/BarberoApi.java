package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.entity.Barbero;
import com.uniminuto.biblioteca.model.BarberoRq;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;

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
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Interfaz que define los endpoints para la gestión de barberos.
 */
@CrossOrigin(origins = "*") // Permite solicitudes desde cualquier origen (para desarrollo)
@RequestMapping("/barbero")
public interface BarberoApi {

    /**
     * Lista todos los barberos registrados.
     * @return Lista de barberos.
     * @throws BadRequestException Excepción si hay un error.
     */
    @GetMapping("/listar")
    ResponseEntity<List<Barbero>> listarBarberos() throws BadRequestException;

    /**
     * Lista barberos por el ID de la sede a la que pertenecen.
     * @param idSede ID de la sede.
     * @return Lista de barberos.
     * @throws BadRequestException Excepción si hay un error.
     */
    @GetMapping("/listar-por-sede")
    ResponseEntity<List<Barbero>> listarBarberosPorSede(@RequestParam Integer idSede) throws BadRequestException;

    /**
     * Obtiene un barbero por su ID.
     * @param idBarbero ID del barbero.
     * @return El barbero encontrado.
     * @throws BadRequestException Excepción si el barbero no existe.
     */
    @GetMapping("/obtener-por-id/{idBarbero}")
    ResponseEntity<Barbero> obtenerBarberoPorId(@PathVariable Integer idBarbero) throws BadRequestException;

    /**
     * Crea un nuevo barbero.
     * @param barberoRq Datos del barbero a crear.
     * @return Respuesta genérica de éxito o error.
     * @throws BadRequestException Excepción si hay errores de validación o duplicidad.
     */
    @PostMapping("/crear")
    ResponseEntity<RespuestaGenericaRs> crearBarbero(@RequestBody BarberoRq barberoRq) throws BadRequestException;

    /**
     * Actualiza un barbero existente.
     * @param barberoRq Datos del barbero a actualizar (debe incluir el ID).
     * @return Respuesta genérica de éxito o error.
     * @throws BadRequestException Excepción si hay errores de validación o el barbero no existe.
     */
    @PutMapping("/actualizar")
    ResponseEntity<RespuestaGenericaRs> actualizarBarbero(@RequestBody BarberoRq barberoRq) throws BadRequestException;

    /**
     * Elimina un barbero por su ID.
     * @param idBarbero ID del barbero a eliminar.
     * @return Respuesta genérica de éxito o error.
     * @throws BadRequestException Excepción si el barbero no existe o no se puede eliminar.
     */
    @DeleteMapping("/eliminar/{idBarbero}")
    ResponseEntity<RespuestaGenericaRs> eliminarBarbero(@PathVariable Integer idBarbero) throws BadRequestException;
}
