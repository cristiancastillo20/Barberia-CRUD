package com.uniminuto.biblioteca.apicontroller; // Manteniendo el paquete base "biblioteca"

import com.uniminuto.biblioteca.api.ServicioApi;
import com.uniminuto.biblioteca.entity.Servicio;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.model.ServicioRq;
import com.uniminuto.biblioteca.services.ServicioService;

import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementación del controlador REST para la gestión de servicios.
 */
@RestController
public class ServicioApiController implements ServicioApi {

    @Autowired
    private ServicioService servicioService;

    @Override
    public ResponseEntity<List<Servicio>> listarServicios() throws BadRequestException {
        return ResponseEntity.ok(servicioService.listarServicios());
    }

    @Override
    public ResponseEntity<Servicio> obtenerServicioPorId(@PathVariable Integer idServicio) throws BadRequestException {
        return ResponseEntity.ok(servicioService.obtenerServicioPorId(idServicio));
    }

    @Override
    public ResponseEntity<RespuestaGenericaRs> crearServicio(@RequestBody ServicioRq servicioRq) throws BadRequestException {
        return ResponseEntity.ok(servicioService.crearServicio(servicioRq));
    }

    @Override
    public ResponseEntity<RespuestaGenericaRs> actualizarServicio(@RequestBody ServicioRq servicioRq) throws BadRequestException {
        return ResponseEntity.ok(servicioService.actualizarServicio(servicioRq));
    }

    @Override
    public ResponseEntity<RespuestaGenericaRs> eliminarServicio(@PathVariable Integer idServicio) throws BadRequestException {
        return ResponseEntity.ok(servicioService.eliminarServicio(idServicio));
    }
}
