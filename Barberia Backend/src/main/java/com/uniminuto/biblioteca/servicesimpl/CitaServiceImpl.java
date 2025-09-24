package com.uniminuto.biblioteca.servicesimpl; // Manteniendo el paquete base "biblioteca"

import com.uniminuto.biblioteca.entity.Barbero;
import com.uniminuto.biblioteca.entity.Cita;
import com.uniminuto.biblioteca.entity.Cliente;
import com.uniminuto.biblioteca.entity.Servicio;
import com.uniminuto.biblioteca.model.CitaRq;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.repository.BarberoRepository;
import com.uniminuto.biblioteca.repository.CitaRepository;
import com.uniminuto.biblioteca.repository.ClienteRepository;
import com.uniminuto.biblioteca.repository.ServicioRepository;
import com.uniminuto.biblioteca.services.CitaService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación de la interfaz CitaService.
 */
@Service
public class CitaServiceImpl implements CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private ClienteRepository clienteRepository; // Para validar Cliente

    @Autowired
    private BarberoRepository barberoRepository; // Para validar Barbero

    @Autowired
    private ServicioRepository servicioRepository; // Para validar Servicio

    @Override
    public List<Cita> listarCitas() {
        return citaRepository.findAllByOrderByFechaAscHoraAsc();
    }

    @Override
    public Cita obtenerCitaPorId(Integer idCita) throws BadRequestException {
        return citaRepository.findById(idCita)
                .orElseThrow(() -> new BadRequestException("Cita no encontrada con ID: " + idCita));
    }

    @Override
    @Transactional
    public RespuestaGenericaRs crearCita(CitaRq citaRq) throws BadRequestException {
        // 1. Validar campos obligatorios del Request
        if (citaRq.getFecha() == null || citaRq.getHora() == null ||
            citaRq.getIdCliente() == null || citaRq.getIdBarbero() == null ||
            citaRq.getIdServicio() == null) {
            throw new BadRequestException("Fecha, hora, ID de cliente, barbero y servicio son obligatorios.");
        }

        // 2. Validar que la fecha no sea pasada
        if (citaRq.getFecha().isBefore(LocalDate.now())) {
            throw new BadRequestException("No se puede agendar una cita en una fecha pasada.");
        }
        // Opcional: Si la fecha es hoy, validar que la hora no sea pasada
        if (citaRq.getFecha().isEqual(LocalDate.now()) && citaRq.getHora().isBefore(LocalTime.now())) {
            throw new BadRequestException("No se puede agendar una cita en una hora pasada para hoy.");
        }


        // 3. Validar existencia de Cliente, Barbero y Servicio
        Cliente cliente = clienteRepository.findById(citaRq.getIdCliente())
                .orElseThrow(() -> new BadRequestException("Cliente no encontrado con ID: " + citaRq.getIdCliente()));

        Barbero barbero = barberoRepository.findById(citaRq.getIdBarbero())
                .orElseThrow(() -> new BadRequestException("Barbero no encontrado con ID: " + citaRq.getIdBarbero()));

        Servicio servicio = servicioRepository.findById(citaRq.getIdServicio())
                .orElseThrow(() -> new BadRequestException("Servicio no encontrado con ID: " + citaRq.getIdServicio()));

        // 4. Validar disponibilidad del barbero (no tener otra cita a la misma hora)
        Optional<Cita> citaExistente = citaRepository.findByFechaAndHoraAndBarbero_IdBarbero(
                citaRq.getFecha(), citaRq.getHora(), citaRq.getIdBarbero());
        if (citaExistente.isPresent()) {
            throw new BadRequestException("El barbero ya tiene una cita agendada para esa fecha y hora.");
        }

        // 5. Crear la entidad Cita
        Cita cita = new Cita();
        cita.setFecha(citaRq.getFecha());
        cita.setHora(citaRq.getHora());
        cita.setCliente(cliente);
        cita.setBarbero(barbero);
        cita.setServicio(servicio);

        try {
            citaRepository.save(cita);
            return new RespuestaGenericaRs(true, "Cita agendada exitosamente.");
        } catch (Exception e) {
            throw new BadRequestException("Error al agendar la cita: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public RespuestaGenericaRs actualizarCita(CitaRq citaRq) throws BadRequestException {
        // 1. Validar ID de la cita
        if (citaRq.getIdCita() == null) {
            throw new BadRequestException("El ID de la cita es obligatorio para la actualización.");
        }

        // 2. Buscar cita existente
        Cita citaActual = citaRepository.findById(citaRq.getIdCita())
                .orElseThrow(() -> new BadRequestException("Cita no encontrada con ID: " + citaRq.getIdCita()));

        // 3. Validar y obtener nuevas entidades relacionadas si los IDs cambian
        Cliente nuevoCliente = citaActual.getCliente();
        if (citaRq.getIdCliente() != null && !citaRq.getIdCliente().equals(citaActual.getCliente().getIdCliente())) {
            nuevoCliente = clienteRepository.findById(citaRq.getIdCliente())
                    .orElseThrow(() -> new BadRequestException("Nuevo cliente no encontrado con ID: " + citaRq.getIdCliente()));
        }

        Barbero nuevoBarbero = citaActual.getBarbero();
        if (citaRq.getIdBarbero() != null && !citaRq.getIdBarbero().equals(citaActual.getBarbero().getIdBarbero())) {
            nuevoBarbero = barberoRepository.findById(citaRq.getIdBarbero())
                    .orElseThrow(() -> new BadRequestException("Nuevo barbero no encontrado con ID: " + citaRq.getIdBarbero()));
        }

        Servicio nuevoServicio = citaActual.getServicio();
        if (citaRq.getIdServicio() != null && !citaRq.getIdServicio().equals(citaActual.getServicio().getIdServicio())) {
            nuevoServicio = servicioRepository.findById(citaRq.getIdServicio())
                    .orElseThrow(() -> new BadRequestException("Nuevo servicio no encontrado con ID: " + citaRq.getIdServicio()));
        }

        // 4. Validar disponibilidad del barbero si la fecha/hora/barbero cambian
        LocalDate fechaCita = citaRq.getFecha() != null ? citaRq.getFecha() : citaActual.getFecha();
        LocalTime horaCita = citaRq.getHora() != null ? citaRq.getHora() : citaActual.getHora();
        Integer idBarberoCita = nuevoBarbero.getIdBarbero(); // Siempre usaremos el ID del barbero (nuevo o actual)

        // Si la fecha, hora o barbero cambian, o si se mantiene igual pero es la misma cita, validar disponibilidad
        if ((citaRq.getFecha() != null && !citaRq.getFecha().equals(citaActual.getFecha())) ||
            (citaRq.getHora() != null && !citaRq.getHora().equals(citaActual.getHora())) ||
            (citaRq.getIdBarbero() != null && !citaRq.getIdBarbero().equals(citaActual.getBarbero().getIdBarbero()))) {

            Optional<Cita> citaConflicto = citaRepository.findByFechaAndHoraAndBarbero_IdBarbero(
                    fechaCita, horaCita, idBarberoCita);

            if (citaConflicto.isPresent() && !citaConflicto.get().getIdCita().equals(citaActual.getIdCita())) {
                throw new BadRequestException("El barbero ya tiene otra cita agendada para la nueva fecha y hora.");
            }
        }
        
        // 5. Validar que la fecha no sea pasada (para la nueva fecha si se actualiza)
        if (fechaCita.isBefore(LocalDate.now())) {
            throw new BadRequestException("No se puede actualizar una cita a una fecha pasada.");
        }
        // Opcional: Si la fecha es hoy, validar que la hora no sea pasada
        if (fechaCita.isEqual(LocalDate.now()) && horaCita.isBefore(LocalTime.now())) {
            throw new BadRequestException("No se puede actualizar una cita a una hora pasada para hoy.");
        }


        // 6. Actualizar campos si se proporcionan en el request
        if (citaRq.getFecha() != null) {
            citaActual.setFecha(citaRq.getFecha());
        }
        if (citaRq.getHora() != null) {
            citaActual.setHora(citaRq.getHora());
        }
        citaActual.setCliente(nuevoCliente);
        citaActual.setBarbero(nuevoBarbero);
        citaActual.setServicio(nuevoServicio);

        try {
            citaRepository.save(citaActual);
            return new RespuestaGenericaRs(true, "Cita actualizada exitosamente.");
        } catch (Exception e) {
            throw new BadRequestException("Error al actualizar la cita: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public RespuestaGenericaRs eliminarCita(Integer idCita) throws BadRequestException {
        if (!citaRepository.existsById(idCita)) {
            throw new BadRequestException("Cita no encontrada con ID: " + idCita);
        }
        try {
            citaRepository.deleteById(idCita);
            return new RespuestaGenericaRs(true, "Cita eliminada exitosamente.");
        } catch (Exception e) {
            // Considera manejar ConstraintViolationException si la cita tiene pagos asociados
            throw new BadRequestException("Error al eliminar la cita: " + e.getMessage());
        }
    }

    @Override
    public List<Cita> listarCitasPorFechaYBarbero(LocalDate fecha, Integer idBarbero) throws BadRequestException {
        // Opcional: Validar existencia del barbero si no se hace en findByFechaAndBarbero_IdBarbero
        barberoRepository.findById(idBarbero)
                .orElseThrow(() -> new BadRequestException("Barbero no encontrado con ID: " + idBarbero));

        List<Cita> citas = citaRepository.findByFechaAndBarbero_IdBarberoOrderByHoraAsc(fecha, idBarbero);
        if (citas.isEmpty()) {
            // No lanzar excepción si no hay citas, solo devolver lista vacía o mensaje
            // throw new BadRequestException("No se encontraron citas para la fecha y barbero especificados.");
            System.out.println("No se encontraron citas para la fecha " + fecha + " y barbero " + idBarbero);
        }
        return citas;
    }

    @Override
    public List<Cita> listarCitasPorCliente(Integer idCliente) throws BadRequestException {
        // Opcional: Validar existencia del cliente
        clienteRepository.findById(idCliente)
                .orElseThrow(() -> new BadRequestException("Cliente no encontrado con ID: " + idCliente));

        List<Cita> citas = citaRepository.findByCliente_IdClienteOrderByFechaDescHoraDesc(idCliente);
        if (citas.isEmpty()) {
            System.out.println("No se encontraron citas para el cliente " + idCliente);
        }
        return citas;
    }
}
