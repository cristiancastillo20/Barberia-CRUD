package com.uniminuto.biblioteca.servicesimpl; // Manteniendo el paquete base "biblioteca"

import com.uniminuto.biblioteca.entity.Sede;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.model.SedeRq;
import com.uniminuto.biblioteca.repository.SedeRepository;
import com.uniminuto.biblioteca.services.SedeService;

import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación de la interfaz SedeService.
 */
@Service
public class SedeServiceImpl implements SedeService {

    @Autowired
    private SedeRepository sedeRepository;

    @Override
    public List<Sede> listarSedes() {
        return sedeRepository.findAll(); // JpaRepository ya provee findAll
    }

    @Override
    public Sede obtenerSedePorId(Integer idSede) throws BadRequestException {
        return sedeRepository.findById(idSede)
                .orElseThrow(() -> new BadRequestException("Sede no encontrada con ID: " + idSede));
    }

    @Override
    @Transactional
    public RespuestaGenericaRs crearSede(SedeRq sedeRq) throws BadRequestException {
        // Validar campos obligatorios
        if (sedeRq.getNombre() == null || sedeRq.getNombre().trim().isEmpty() ||
            sedeRq.getDireccion() == null || sedeRq.getDireccion().trim().isEmpty() ||
            sedeRq.getTelefono() == null || sedeRq.getTelefono().trim().isEmpty()) {
            throw new BadRequestException("Nombre, dirección y teléfono son campos obligatorios.");
        }

        // Validar duplicidad por nombre (si se considera único)
        if (sedeRepository.existsByNombre(sedeRq.getNombre())) {
            throw new BadRequestException("Ya existe una sede con el nombre proporcionado.");
        }

        Sede sede = new Sede();
        sede.setNombre(sedeRq.getNombre());
        sede.setDireccion(sedeRq.getDireccion());
        sede.setTelefono(sedeRq.getTelefono());

        try {
            sedeRepository.save(sede);
            return new RespuestaGenericaRs(true, "Sede creada exitosamente.");
        } catch (Exception e) {
            throw new BadRequestException("Error al crear la sede: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public RespuestaGenericaRs actualizarSede(SedeRq sedeRq) throws BadRequestException {
        // Validar ID de la sede
        if (sedeRq.getIdSede() == null) {
            throw new BadRequestException("El ID de la sede es obligatorio para la actualización.");
        }

        // Buscar sede existente
        Sede sedeActual = sedeRepository.findById(sedeRq.getIdSede())
                .orElseThrow(() -> new BadRequestException("Sede no encontrada con ID: " + sedeRq.getIdSede()));

        // Verificar si el nombre cambió y si el nuevo nombre ya existe para otra sede
        boolean nombreCambiado = sedeRq.getNombre() != null && !sedeActual.getNombre().equals(sedeRq.getNombre());

        if (nombreCambiado) {
            if (sedeRepository.findByNombre(sedeRq.getNombre())
                    .filter(s -> !s.getIdSede().equals(sedeActual.getIdSede())).isPresent()) {
                throw new BadRequestException("Ya existe otra sede con el nombre proporcionado.");
            }
        }

        // Actualizar campos si se proporcionan en el request
        if (sedeRq.getNombre() != null) {
            sedeActual.setNombre(sedeRq.getNombre());
        }
        if (sedeRq.getDireccion() != null) {
            sedeActual.setDireccion(sedeRq.getDireccion());
        }
        if (sedeRq.getTelefono() != null) {
            sedeActual.setTelefono(sedeRq.getTelefono());
        }

        try {
            sedeRepository.save(sedeActual);
            return new RespuestaGenericaRs(true, "Sede actualizada exitosamente.");
        } catch (Exception e) {
            throw new BadRequestException("Error al actualizar la sede: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public RespuestaGenericaRs eliminarSede(Integer idSede) throws BadRequestException {
        if (!sedeRepository.existsById(idSede)) {
            throw new BadRequestException("Sede no encontrada con ID: " + idSede);
        }
        try {
            sedeRepository.deleteById(idSede);
            return new RespuestaGenericaRs(true, "Sede eliminada exitosamente.");
        } catch (Exception e) {
            // Considera manejar ConstraintViolationException si la sede tiene barberos asociados
            throw new BadRequestException("Error al eliminar la sede: " + e.getMessage());
        }
    }
}
