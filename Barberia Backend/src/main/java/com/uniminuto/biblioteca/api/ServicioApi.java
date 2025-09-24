package com.uniminuto.biblioteca.api; // Manteniendo el paquete base "biblioteca"

import com.uniminuto.biblioteca.entity.Servicio;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.model.ServicioRq;

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

/**
 * Interfaz que define los endpoints para la gestión de servicios.
 */
@CrossOrigin(origins = "*") // Permite solicitudes desde cualquier origen (para desarrollo)
@RequestMapping("/servicio") // El context-path '/biblioteca/v1' se añade globalmente en application.properties
public interface ServicioApi {

    /**
     * Lista todos los servicios registrados.
     * @return Lista de servicios.
     * @throws BadRequestException Excepción si hay un error.
     */
    @GetMapping("/listar")
    ResponseEntity<List<Servicio>> listarServicios() throws BadRequestException;

    /**
     * Obtiene un servicio por su ID.
     * @param idServicio ID del servicio.
     * @return El servicio encontrado.
     * @throws BadRequestException Excepción si el servicio no existe.
     */
    @GetMapping("/obtener-por-id/{idServicio}")
    ResponseEntity<Servicio> obtenerServicioPorId(@PathVariable Integer idServicio) throws BadRequestException;

    /**
     * Crea un nuevo servicio.
     * @param servicioRq Datos del servicio a crear.
     * @return Respuesta genérica de éxito o error.
     * @throws BadRequestException Excepción si hay errores de validación o duplicidad.
     */
    @PostMapping("/crear")
    ResponseEntity<RespuestaGenericaRs> crearServicio(@RequestBody ServicioRq servicioRq) throws BadRequestException;

    /**
     * Actualiza un servicio existente.
     * @param servicioRq Datos del servicio a actualizar (debe incluir el ID).
     * @return Respuesta genérica de éxito o error.
     * @throws BadRequestException Excepción si hay errores de validación o el servicio no existe.
     */
    @PutMapping("/actualizar")
    ResponseEntity<RespuestaGenericaRs> actualizarServicio(@RequestBody ServicioRq servicioRq) throws BadRequestException;

    /**
     * Elimina un servicio por su ID.
     * @param idServicio ID del servicio a eliminar.
     * @return Respuesta genérica de éxito o error.
     * @throws BadRequestException Excepción si el servicio no existe o no se puede eliminar.
     */
    @DeleteMapping("/eliminar/{idServicio}")
    ResponseEntity<RespuestaGenericaRs> eliminarServicio(@PathVariable Integer idServicio) throws BadRequestException;
}
