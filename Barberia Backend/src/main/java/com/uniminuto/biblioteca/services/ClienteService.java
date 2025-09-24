package com.uniminuto.biblioteca.services; // Manteniendo el paquete base "biblioteca"

import com.uniminuto.biblioteca.entity.Cliente;
import com.uniminuto.biblioteca.model.ClienteRq;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 * Interfaz que define los servicios de negocio para la gestión de clientes.
 */
public interface ClienteService {

    /**
     * Obtiene el listado de todos los clientes.
     * @return Lista de clientes.
     */
    List<Cliente> listarClientes();

    /**
     * Obtiene un cliente por su ID.
     * @param idCliente ID del cliente.
     * @return El objeto Cliente.
     * @throws BadRequestException Si el cliente no se encuentra.
     */
    Cliente obtenerClientePorId(Integer idCliente) throws BadRequestException;

    /**
     * Crea un nuevo cliente.
     * @param clienteRq Objeto de solicitud con los datos del cliente.
     * @return Respuesta genérica de éxito o error.
     * @throws BadRequestException Si hay un error de validación o duplicidad.
     */
    RespuestaGenericaRs crearCliente(ClienteRq clienteRq) throws BadRequestException;

    /**
     * Actualiza un cliente existente.
     * @param clienteRq Objeto de solicitud con los datos actualizados del cliente.
     * @return Respuesta genérica de éxito o error.
     * @throws BadRequestException Si hay un error de validación o el cliente no existe.
     */
    RespuestaGenericaRs actualizarCliente(ClienteRq clienteRq) throws BadRequestException;

    /**
     * Elimina un cliente por su ID.
     * @param idCliente ID del cliente a eliminar.
     * @return Respuesta genérica de éxito o error.
     * @throws BadRequestException Si el cliente no existe o no se puede eliminar.
     */
    RespuestaGenericaRs eliminarCliente(Integer idCliente) throws BadRequestException;
}
