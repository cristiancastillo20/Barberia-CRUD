package com.uniminuto.biblioteca.apicontroller; // Manteniendo el paquete base "biblioteca"

import com.uniminuto.biblioteca.api.CitaApi;
import com.uniminuto.biblioteca.entity.Cita;
import com.uniminuto.biblioteca.model.CitaRq;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.services.CitaService;

import java.time.LocalDate;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementación del controlador REST para la gestión de citas.
 */
@RestController
public class CitaApiController implements CitaApi {

    @Autowired
    private CitaService citaService;

    @Override
    public ResponseEntity<List<Cita>> listarCitas() throws BadRequestException {
        return ResponseEntity.ok(citaService.listarCitas());
    }

    @Override
    public ResponseEntity<Cita> obtenerCitaPorId(@PathVariable Integer idCita) throws BadRequestException {
        return ResponseEntity.ok(citaService.obtenerCitaPorId(idCita));
    }

    @Override
    public ResponseEntity<RespuestaGenericaRs> crearCita(@RequestBody CitaRq citaRq) throws BadRequestException {
        return ResponseEntity.ok(citaService.crearCita(citaRq));
    }

    @Override
    public ResponseEntity<RespuestaGenericaRs> actualizarCita(@RequestBody CitaRq citaRq) throws BadRequestException {
        return ResponseEntity.ok(citaService.actualizarCita(citaRq));
    }

    @Override
    public ResponseEntity<RespuestaGenericaRs> eliminarCita(@PathVariable Integer idCita) throws BadRequestException {
        return ResponseEntity.ok(citaService.eliminarCita(idCita));
    }

    @Override
    public ResponseEntity<List<Cita>> listarCitasPorFechaYBarbero(
            @RequestParam LocalDate fecha, @RequestParam Integer idBarbero) throws BadRequestException {
        return ResponseEntity.ok(citaService.listarCitasPorFechaYBarbero(fecha, idBarbero));
    }

    @Override
    public ResponseEntity<List<Cita>> listarCitasPorCliente(@PathVariable Integer idCliente) throws BadRequestException {
        return ResponseEntity.ok(citaService.listarCitasPorCliente(idCliente));
    }
}
