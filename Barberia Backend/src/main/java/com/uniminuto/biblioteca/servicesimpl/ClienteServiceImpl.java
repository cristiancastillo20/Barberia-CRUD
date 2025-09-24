package com.uniminuto.biblioteca.servicesimpl; // Manteniendo el paquete base "biblioteca"

import com.uniminuto.biblioteca.entity.Cliente;
import com.uniminuto.biblioteca.model.ClienteRq;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.repository.ClienteRepository;
import com.uniminuto.biblioteca.services.ClienteService;

import java.time.LocalDate;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación de la interfaz ClienteService.
 */
@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public List<Cliente> listarClientes() {
        return clienteRepository.findAllByOrderByFechaRegistroAsc();
    }

    @Override
    public Cliente obtenerClientePorId(Integer idCliente) throws BadRequestException {
        return clienteRepository.findById(idCliente)
                .orElseThrow(() -> new BadRequestException("Cliente no encontrado con ID: " + idCliente));
    }

    @Override
    @Transactional
    public RespuestaGenericaRs crearCliente(ClienteRq clienteRq) throws BadRequestException {
        // Validar campos obligatorios
        if (clienteRq.getNombre() == null || clienteRq.getNombre().trim().isEmpty() ||
            clienteRq.getApellido() == null || clienteRq.getApellido().trim().isEmpty() ||
            clienteRq.getTelefono() == null || clienteRq.getTelefono().trim().isEmpty()) {
            throw new BadRequestException("Nombre, apellido y teléfono son campos obligatorios.");
        }

        // Validar duplicidad por teléfono (considerando que el teléfono debe ser único)
        if (clienteRepository.existsByTelefono(clienteRq.getTelefono())) {
            throw new BadRequestException("Ya existe un cliente con el número de teléfono proporcionado.");
        }

        Cliente cliente = new Cliente();
        cliente.setNombre(clienteRq.getNombre());
        cliente.setApellido(clienteRq.getApellido());
        cliente.setTelefono(clienteRq.getTelefono());
        cliente.setCorreo(clienteRq.getCorreo());
        // Si no se proporciona fechaRegistro en el RQ, se usará el valor DEFAULT de la DB (CURDATE())
        // Si se proporciona, se usará la del RQ.
        cliente.setFechaRegistro(clienteRq.getFechaRegistro() != null ? clienteRq.getFechaRegistro() : LocalDate.now());


        try {
            clienteRepository.save(cliente);
            return new RespuestaGenericaRs(true, "Cliente creado exitosamente.");
        } catch (Exception e) {
            throw new BadRequestException("Error al crear el cliente: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public RespuestaGenericaRs actualizarCliente(ClienteRq clienteRq) throws BadRequestException {
        // Validar ID del cliente
        if (clienteRq.getIdCliente()== null) {
            throw new BadRequestException("El ID del cliente es obligatorio para la actualización.");
        }

        // Buscar cliente existente
        Cliente clienteActual = clienteRepository.findById(clienteRq.getIdCliente())
                .orElseThrow(() -> new BadRequestException("Cliente no encontrado con ID: " + clienteRq.getIdCliente()));

        // Verificar si el teléfono cambió y si el nuevo teléfono ya existe para otro cliente
        boolean telefonoCambiado = clienteRq.getTelefono() != null && !clienteActual.getTelefono().equals(clienteRq.getTelefono());

        if (telefonoCambiado) {
            if (clienteRepository.findByTelefono(clienteRq.getTelefono())
                    .filter(c -> !c.getIdCliente().equals(clienteActual.getIdCliente())).isPresent()) {
                throw new BadRequestException("Ya existe otro cliente con el número de teléfono proporcionado.");
            }
        }

        // Actualizar campos si se proporcionan en el request
        if (clienteRq.getNombre() != null) {
            clienteActual.setNombre(clienteRq.getNombre());
        }
        if (clienteRq.getApellido() != null) {
            clienteActual.setApellido(clienteRq.getApellido());
        }
        if (clienteRq.getTelefono() != null) {
            clienteActual.setTelefono(clienteRq.getTelefono());
        }
        if (clienteRq.getCorreo() != null) {
            clienteActual.setCorreo(clienteRq.getCorreo());
        }
        // La fecha de registro no suele actualizarse, pero si se desea, se podría añadir lógica.
        // Por ahora, se mantiene la fecha original o se actualiza si se envía en el RQ (menos común).
        if (clienteRq.getFechaRegistro() != null) {
            clienteActual.setFechaRegistro(clienteRq.getFechaRegistro());
        }


        try {
            clienteRepository.save(clienteActual);
            return new RespuestaGenericaRs(true, "Cliente actualizado exitosamente.");
        } catch (Exception e) {
            throw new BadRequestException("Error al actualizar el cliente: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public RespuestaGenericaRs eliminarCliente(Integer idCliente) throws BadRequestException {
        if (!clienteRepository.existsById(idCliente)) {
            throw new BadRequestException("Cliente no encontrado con ID: " + idCliente);
        }
        try {
            clienteRepository.deleteById(idCliente);
            return new RespuestaGenericaRs(true, "Cliente eliminado exitosamente.");
        } catch (Exception e) {
            // Considera manejar ConstraintViolationException si el cliente tiene citas asociadas
            throw new BadRequestException("Error al eliminar el cliente: " + e.getMessage());
        }
    }
}
