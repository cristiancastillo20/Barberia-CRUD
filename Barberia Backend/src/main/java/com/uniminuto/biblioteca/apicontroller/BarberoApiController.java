package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.api.BarberoApi;
import com.uniminuto.biblioteca.entity.Barbero;
import com.uniminuto.biblioteca.model.BarberoRq;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.services.BarberoService;

import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementación del controlador REST para la gestión de barberos.
 */
@RestController
public class BarberoApiController implements BarberoApi {

    @Autowired
    private BarberoService barberoService;

    @Override
    public ResponseEntity<List<Barbero>> listarBarberos() throws BadRequestException {
        return ResponseEntity.ok(barberoService.listarBarberos());
    }

    @Override
    public ResponseEntity<List<Barbero>> listarBarberosPorSede(@RequestParam Integer idSede) throws BadRequestException {
        return ResponseEntity.ok(barberoService.listarBarberosPorSede(idSede));
    }

    @Override
    public ResponseEntity<Barbero> obtenerBarberoPorId(@PathVariable Integer idBarbero) throws BadRequestException {
        return ResponseEntity.ok(barberoService.obtenerBarberoPorId(idBarbero));
    }

    @Override
    public ResponseEntity<RespuestaGenericaRs> crearBarbero(@RequestBody BarberoRq barberoRq) throws BadRequestException {
        return ResponseEntity.ok(barberoService.crearBarbero(barberoRq));
    }

    @Override
    public ResponseEntity<RespuestaGenericaRs> actualizarBarbero(@RequestBody BarberoRq barberoRq) throws BadRequestException {
        return ResponseEntity.ok(barberoService.actualizarBarbero(barberoRq));
    }

    @Override
    public ResponseEntity<RespuestaGenericaRs> eliminarBarbero(@PathVariable Integer idBarbero) throws BadRequestException {
        return ResponseEntity.ok(barberoService.eliminarBarbero(idBarbero));
    }
}
