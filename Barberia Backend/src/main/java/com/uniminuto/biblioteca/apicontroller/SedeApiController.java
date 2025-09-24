package com.uniminuto.biblioteca.apicontroller; // Manteniendo el paquete base "biblioteca"

import com.uniminuto.biblioteca.api.SedeApi;
import com.uniminuto.biblioteca.entity.Sede;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.model.SedeRq;
import com.uniminuto.biblioteca.services.SedeService;

import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementación del controlador REST para la gestión de sedes.
 */
@RestController
public class SedeApiController implements SedeApi {

    @Autowired
    private SedeService sedeService;

    @Override
    public ResponseEntity<List<Sede>> listarSedes() throws BadRequestException {
        return ResponseEntity.ok(sedeService.listarSedes());
    }

    @Override
    public ResponseEntity<Sede> obtenerSedePorId(@PathVariable Integer idSede) throws BadRequestException {
        return ResponseEntity.ok(sedeService.obtenerSedePorId(idSede));
    }

    @Override
    public ResponseEntity<RespuestaGenericaRs> crearSede(@RequestBody SedeRq sedeRq) throws BadRequestException {
        return ResponseEntity.ok(sedeService.crearSede(sedeRq));
    }

    @Override
    public ResponseEntity<RespuestaGenericaRs> actualizarSede(@RequestBody SedeRq sedeRq) throws BadRequestException {
        return ResponseEntity.ok(sedeService.actualizarSede(sedeRq));
    }

    @Override
    public ResponseEntity<RespuestaGenericaRs> eliminarSede(@PathVariable Integer idSede) throws BadRequestException {
        return ResponseEntity.ok(sedeService.eliminarSede(idSede));
    }
}
