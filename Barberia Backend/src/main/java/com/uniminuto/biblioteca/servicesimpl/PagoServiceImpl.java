package com.uniminuto.biblioteca.servicesimpl; // Manteniendo el paquete base "biblioteca"

import com.uniminuto.biblioteca.entity.Cita;
import com.uniminuto.biblioteca.entity.Pago;
import com.uniminuto.biblioteca.model.PagoRq;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.repository.CitaRepository; // Para validar Cita
import com.uniminuto.biblioteca.repository.PagoRepository;
import com.uniminuto.biblioteca.services.PagoService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación de la interfaz PagoService.
 */
@Service
public class PagoServiceImpl implements PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private CitaRepository citaRepository; // Inyectar CitaRepository para validar la cita

    @Override
    public List<Pago> listarPagos() {
        return pagoRepository.findAll(); // JpaRepository ya provee findAll
    }

    @Override
    public Pago obtenerPagoPorId(Integer idPago) throws BadRequestException {
        return pagoRepository.findById(idPago)
                .orElseThrow(() -> new BadRequestException("Pago no encontrado con ID: " + idPago));
    }

    @Override
    @Transactional
    public RespuestaGenericaRs crearPago(PagoRq pagoRq) throws BadRequestException {
        // 1. Validar campos obligatorios del Request
        if (pagoRq.getMonto() == null || pagoRq.getMonto().compareTo(BigDecimal.ZERO) <= 0 || // Monto no nulo y positivo
            pagoRq.getFechaPago() == null ||
            pagoRq.getMetodoPago() == null ||
            pagoRq.getIdCita() == null) {
            throw new BadRequestException("Monto (positivo), fecha de pago, método de pago e ID de cita son obligatorios.");
        }

        // 2. Validar existencia de la Cita
        Cita cita = citaRepository.findById(pagoRq.getIdCita())
                .orElseThrow(() -> new BadRequestException("Cita no encontrada con ID: " + pagoRq.getIdCita()));

        // 3. Crear la entidad Pago
        Pago pago = new Pago();
        pago.setMonto(pagoRq.getMonto());
        pago.setFechaPago(pagoRq.getFechaPago());
        pago.setMetodoPago(pagoRq.getMetodoPago());
        pago.setCita(cita); // Asigna el objeto Cita completo

        try {
            pagoRepository.save(pago);
            return new RespuestaGenericaRs(true, "Pago registrado exitosamente.");
        } catch (Exception e) {
            throw new BadRequestException("Error al registrar el pago: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public RespuestaGenericaRs actualizarPago(PagoRq pagoRq) throws BadRequestException {
        // 1. Validar ID del pago
        if (pagoRq.getIdPago() == null) {
            throw new BadRequestException("El ID del pago es obligatorio para la actualización.");
        }

        // 2. Buscar pago existente
        Pago pagoActual = pagoRepository.findById(pagoRq.getIdPago())
                .orElseThrow(() -> new BadRequestException("Pago no encontrado con ID: " + pagoRq.getIdPago()));

        // 3. Validar y obtener nueva entidad Cita relacionada si el ID cambia
        Cita nuevaCita = pagoActual.getCita();
        if (pagoRq.getIdCita() != null && !pagoRq.getIdCita().equals(pagoActual.getCita().getIdCita())) {
            nuevaCita = citaRepository.findById(pagoRq.getIdCita())
                    .orElseThrow(() -> new BadRequestException("Nueva cita no encontrada con ID: " + pagoRq.getIdCita()));
        }

        // 4. Actualizar campos si se proporcionan en el request
        if (pagoRq.getMonto() != null && pagoRq.getMonto().compareTo(BigDecimal.ZERO) >= 0) { // Monto no nulo y no negativo
            pagoActual.setMonto(pagoRq.getMonto());
        } else if (pagoRq.getMonto() != null && pagoRq.getMonto().compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException("El monto del pago no puede ser negativo.");
        }
        
        if (pagoRq.getFechaPago() != null) {
            pagoActual.setFechaPago(pagoRq.getFechaPago());
        }
        if (pagoRq.getMetodoPago() != null) {
            pagoActual.setMetodoPago(pagoRq.getMetodoPago());
        }
        pagoActual.setCita(nuevaCita); // Asigna la cita (actual o nueva)

        try {
            pagoRepository.save(pagoActual);
            return new RespuestaGenericaRs(true, "Pago actualizado exitosamente.");
        } catch (Exception e) {
            throw new BadRequestException("Error al actualizar el pago: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public RespuestaGenericaRs eliminarPago(Integer idPago) throws BadRequestException {
        if (!pagoRepository.existsById(idPago)) {
            throw new BadRequestException("Pago no encontrado con ID: " + idPago);
        }
        try {
            pagoRepository.deleteById(idPago);
            return new RespuestaGenericaRs(true, "Pago eliminado exitosamente.");
        } catch (Exception e) {
            throw new BadRequestException("Error al eliminar el pago: " + e.getMessage());
        }
    }

    @Override
    public List<Pago> listarPagosPorCita(Integer idCita) throws BadRequestException {
        // Opcional: Validar existencia de la cita
        citaRepository.findById(idCita)
                .orElseThrow(() -> new BadRequestException("Cita no encontrada con ID: " + idCita));
        
        List<Pago> pagos = pagoRepository.findByCita_IdCita(idCita);
        if (pagos.isEmpty()) {
            System.out.println("No se encontraron pagos para la cita con ID: " + idCita);
        }
        return pagos;
    }

    @Override
    public List<Pago> listarPagosPorMetodo(Pago.MetodoPago metodoPago) throws BadRequestException {
        List<Pago> pagos = pagoRepository.findByMetodoPago(metodoPago);
        if (pagos.isEmpty()) {
            System.out.println("No se encontraron pagos con el método: " + metodoPago.name());
        }
        return pagos;
    }

    @Override
    public List<Pago> listarPagosPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) throws BadRequestException {
        if (fechaInicio == null || fechaFin == null) {
            throw new BadRequestException("Ambas fechas (inicio y fin) son obligatorias para el rango.");
        }
        if (fechaFin.isBefore(fechaInicio)) {
            throw new BadRequestException("La fecha de fin no puede ser anterior a la fecha de inicio.");
        }
        List<Pago> pagos = pagoRepository.findByFechaPagoBetween(fechaInicio, fechaFin);
        if (pagos.isEmpty()) {
            System.out.println("No se encontraron pagos en el rango de fechas [" + fechaInicio + " - " + fechaFin + "]");
        }
        return pagos;
    }
}
