package com.uniminuto.biblioteca.api; // Manteniendo el paquete base "biblioteca"

import com.uniminuto.biblioteca.entity.Cliente;
import com.uniminuto.biblioteca.model.ClienteRq;
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

/**
 * Interfaz que define los endpoints para la gestión de clientes.
 */
@CrossOrigin(origins = "*") // Permite solicitudes desde cualquier origen (para desarrollo)
@RequestMapping("/cliente") // El context-path '/biblioteca/v1' se añade globalmente en application.properties
public interface ClienteApi {

    /**
     * Lista todos los clientes registrados.
     * @return Lista de clientes.
     * @throws BadRequestException Excepción si hay un error.
     */
    @GetMapping("/listar")
    ResponseEntity<List<Cliente>> listarClientes() throws BadRequestException;

    /**
     * Obtiene un cliente por su ID.
     * @param idCliente ID del cliente.
     * @return El cliente encontrado.
     * @throws BadRequestException Excepción si el cliente no existe.
     */
    @GetMapping("/obtener-por-id/{idCliente}")
    ResponseEntity<Cliente> obtenerClientePorId(@PathVariable Integer idCliente) throws BadRequestException;

    /**
     * Crea un nuevo cliente.
     * @param clienteRq Datos del cliente a crear.
     * @return Respuesta genérica de éxito o error.
     * @throws BadRequestException Excepción si hay errores de validación o duplicidad.
     */
    @PostMapping("/crear")
    ResponseEntity<RespuestaGenericaRs> crearCliente(@RequestBody ClienteRq clienteRq) throws BadRequestException;

    /**
     * Actualiza un cliente existente.
     * @param clienteRq Datos del cliente a actualizar (debe incluir el ID).
     * @return Respuesta genérica de éxito o error.
     * @throws BadRequestException Excepción si hay errores de validación o el cliente no existe.
     */
    @PutMapping("/actualizar")
    ResponseEntity<RespuestaGenericaRs> actualizarCliente(@RequestBody ClienteRq clienteRq) throws BadRequestException;

    /**
     * Elimina un cliente por su ID.
     * @param idCliente ID del cliente a eliminar.
     * @return Respuesta genérica de éxito o error.
     * @throws BadRequestException Excepción si el cliente no existe o no se puede eliminar.
     */
    @DeleteMapping("/eliminar/{idCliente}")
    ResponseEntity<RespuestaGenericaRs> eliminarCliente(@PathVariable Integer idCliente) throws BadRequestException;
}
