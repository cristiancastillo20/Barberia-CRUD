package com.uniminuto.biblioteca.servicesimpl; // Manteniendo el paquete base "biblioteca"

import com.uniminuto.biblioteca.entity.Servicio;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.model.ServicioRq;
import com.uniminuto.biblioteca.repository.ServicioRepository;
import com.uniminuto.biblioteca.services.ServicioService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación de la interfaz ServicioService.
 */
@Service
public class ServicioServiceImpl implements ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    @Override
    public List<Servicio> listarServicios() {
        // --- CAMBIO: Ahora usa el nuevo método para ordenar por ID ---
        return servicioRepository.findAllByOrderByIdServicioAsc();
    }

    @Override
    public Servicio obtenerServicioPorId(Integer idServicio) throws BadRequestException {
        return servicioRepository.findById(idServicio)
                .orElseThrow(() -> new BadRequestException("Servicio no encontrado con ID: " + idServicio));
    }

    @Override
    @Transactional
    public RespuestaGenericaRs crearServicio(ServicioRq servicioRq) throws BadRequestException {
        // Validar campos obligatorios
        if (servicioRq.getNombreServicio() == null || servicioRq.getNombreServicio().trim().isEmpty() ||
            servicioRq.getPrecio() == null || servicioRq.getPrecio().compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException("Nombre del servicio y precio son campos obligatorios y el precio debe ser positivo.");
        }

        // Validar duplicidad por nombre de servicio
        if (servicioRepository.existsByNombreServicio(servicioRq.getNombreServicio())) {
            throw new BadRequestException("Ya existe un servicio con el nombre proporcionado.");
        }

        Servicio servicio = new Servicio();
        servicio.setNombreServicio(servicioRq.getNombreServicio());
        servicio.setDescripcion(servicioRq.getDescripcion());
        servicio.setPrecio(servicioRq.getPrecio());

        try {
            servicioRepository.save(servicio);
            return new RespuestaGenericaRs(true, "Servicio creado exitosamente.");
        } catch (Exception e) {
            throw new BadRequestException("Error al crear el servicio: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public RespuestaGenericaRs actualizarServicio(ServicioRq servicioRq) throws BadRequestException {
        // Validar ID del servicio
        if (servicioRq.getIdServicio() == null) {
            throw new BadRequestException("El ID del servicio es obligatorio para la actualización.");
        }

        // Buscar servicio existente
        Servicio servicioActual = servicioRepository.findById(servicioRq.getIdServicio())
                .orElseThrow(() -> new BadRequestException("Servicio no encontrado con ID: " + servicioRq.getIdServicio()));

        // Verificar si el nombre del servicio cambió y si el nuevo nombre ya existe para otro servicio
        boolean nombreServicioCambiado = servicioRq.getNombreServicio() != null && !servicioActual.getNombreServicio().equals(servicioRq.getNombreServicio());

        if (nombreServicioCambiado) {
            if (servicioRepository.findByNombreServicio(servicioRq.getNombreServicio())
                    .filter(s -> !s.getIdServicio().equals(servicioActual.getIdServicio())).isPresent()) {
                throw new BadRequestException("Ya existe otro servicio con el nombre proporcionado.");
            }
        }

        // Actualizar campos si se proporcionan en el request
        // Nota: Asegúrate de que los campos que no se envían en el request (ej. descripcion)
        // se manejen adecuadamente para no ser nulificados si es que no deben serlo.
        // Aquí se actualizan solo si no son null en el servicioRq.
        if (servicioRq.getNombreServicio() != null) {
            servicioActual.setNombreServicio(servicioRq.getNombreServicio());
        }
        // CAMBIO: Asegúrate de que la descripción se actualice incluso si es un String vacío,
        // ya que eso es diferente de null si quieres permitir descripciones en blanco.
        // Si quieres que un String vacío no actualice, deberías añadir un .isEmpty() check.
        // Por ahora, si es null no actualiza, si es "" sí.
        if (servicioRq.getDescripcion() != null) {
            servicioActual.setDescripcion(servicioRq.getDescripcion());
        }
        if (servicioRq.getPrecio() != null && servicioRq.getPrecio().compareTo(BigDecimal.ZERO) >= 0) {
            servicioActual.setPrecio(servicioRq.getPrecio());
        } else if (servicioRq.getPrecio() != null && servicioRq.getPrecio().compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException("El precio del servicio no puede ser negativo.");
        }

        try {
            servicioRepository.save(servicioActual);
            return new RespuestaGenericaRs(true, "Servicio actualizado exitosamente.");
        } catch (Exception e) {
            throw new BadRequestException("Error al actualizar el servicio: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public RespuestaGenericaRs eliminarServicio(Integer idServicio) throws BadRequestException {
        if (!servicioRepository.existsById(idServicio)) {
            throw new BadRequestException("Servicio no encontrado con ID: " + idServicio);
        }
        try {
            servicioRepository.deleteById(idServicio);
            return new RespuestaGenericaRs(true, "Servicio eliminado exitosamente.");
        } catch (Exception e) {
            throw new BadRequestException("Error al eliminar el servicio: " + e.getMessage());
        }
    }
}