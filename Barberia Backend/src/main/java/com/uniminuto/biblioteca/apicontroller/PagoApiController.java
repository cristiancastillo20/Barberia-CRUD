package com.uniminuto.biblioteca.apicontroller; // Manteniendo el paquete base "biblioteca"

import com.uniminuto.biblioteca.api.PagoApi;
import com.uniminuto.biblioteca.entity.Pago;
import com.uniminuto.biblioteca.model.PagoRq;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.services.PagoService;

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
 * Implementación del controlador REST para la gestión de pagos.
 */
@RestController
public class PagoApiController implements PagoApi {

    @Autowired
    private PagoService pagoService;

    @Override
    public ResponseEntity<List<Pago>> listarPagos() throws BadRequestException {
        return ResponseEntity.ok(pagoService.listarPagos());
    }

    @Override
    public ResponseEntity<Pago> obtenerPagoPorId(@PathVariable Integer idPago) throws BadRequestException {
        return ResponseEntity.ok(pagoService.obtenerPagoPorId(idPago));
    }

    @Override
    public ResponseEntity<RespuestaGenericaRs> crearPago(@RequestBody PagoRq pagoRq) throws BadRequestException {
        return ResponseEntity.ok(pagoService.crearPago(pagoRq));
    }

    @Override
    public ResponseEntity<RespuestaGenericaRs> actualizarPago(@RequestBody PagoRq pagoRq) throws BadRequestException {
        return ResponseEntity.ok(pagoService.actualizarPago(pagoRq));
    }

    @Override
    public ResponseEntity<RespuestaGenericaRs> eliminarPago(@PathVariable Integer idPago) throws BadRequestException {
        return ResponseEntity.ok(pagoService.eliminarPago(idPago));
    }

    @Override
    public ResponseEntity<List<Pago>> listarPagosPorCita(@PathVariable Integer idCita) throws BadRequestException {
        return ResponseEntity.ok(pagoService.listarPagosPorCita(idCita));
    }

    @Override
    public ResponseEntity<List<Pago>> listarPagosPorMetodo(@RequestParam Pago.MetodoPago metodoPago) throws BadRequestException {
        return ResponseEntity.ok(pagoService.listarPagosPorMetodo(metodoPago));
    }

    @Override
    public ResponseEntity<List<Pago>> listarPagosPorRangoFechas(
            @RequestParam LocalDate fechaInicio, @RequestParam LocalDate fechaFin) throws BadRequestException {
        return ResponseEntity.ok(pagoService.listarPagosPorRangoFechas(fechaInicio, fechaFin));
    }
}
