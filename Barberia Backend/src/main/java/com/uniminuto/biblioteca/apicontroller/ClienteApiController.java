package com.uniminuto.biblioteca.apicontroller; // Manteniendo el paquete base "biblioteca"

import com.uniminuto.biblioteca.api.ClienteApi;
import com.uniminuto.biblioteca.entity.Cliente;
import com.uniminuto.biblioteca.model.ClienteRq;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.services.ClienteService;

import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementación del controlador REST para la gestión de clientes.
 */
@RestController
public class ClienteApiController implements ClienteApi {

    @Autowired
    private ClienteService clienteService;

    @Override
    public ResponseEntity<List<Cliente>> listarClientes() throws BadRequestException {
        return ResponseEntity.ok(clienteService.listarClientes());
    }

    @Override
    public ResponseEntity<Cliente> obtenerClientePorId(@PathVariable Integer idCliente) throws BadRequestException {
        return ResponseEntity.ok(clienteService.obtenerClientePorId(idCliente));
    }

    @Override
    public ResponseEntity<RespuestaGenericaRs> crearCliente(@RequestBody ClienteRq clienteRq) throws BadRequestException {
        return ResponseEntity.ok(clienteService.crearCliente(clienteRq));
    }

    @Override
    public ResponseEntity<RespuestaGenericaRs> actualizarCliente(@RequestBody ClienteRq clienteRq) throws BadRequestException {
        return ResponseEntity.ok(clienteService.actualizarCliente(clienteRq));
    }

    @Override
    public ResponseEntity<RespuestaGenericaRs> eliminarCliente(@PathVariable Integer idCliente) throws BadRequestException {
        return ResponseEntity.ok(clienteService.eliminarCliente(idCliente));
    }
}
