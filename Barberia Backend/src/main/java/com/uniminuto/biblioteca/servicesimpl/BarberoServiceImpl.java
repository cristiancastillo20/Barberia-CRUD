package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Barbero;
import com.uniminuto.biblioteca.entity.Sede;
import com.uniminuto.biblioteca.model.BarberoRq;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.repository.BarberoRepository;
import com.uniminuto.biblioteca.repository.SedeRepository; // Necesario para validar la sede
import com.uniminuto.biblioteca.services.BarberoService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación de la interfaz BarberoService.
 */
@Service
public class BarberoServiceImpl implements BarberoService {

    @Autowired
    private BarberoRepository barberoRepository;

    @Autowired
    private SedeRepository sedeRepository; // Inyectar SedeRepository para validar la existencia de la sede

    @Override
    public List<Barbero> listarBarberos() {
        return barberoRepository.findAllByOrderByApellidoAscNombreAsc();
    }

    @Override
    public List<Barbero> listarBarberosPorSede(Integer idSede) throws BadRequestException {
        // Validar si la sede existe antes de buscar barberos por ella
        Optional<Sede> optSede = sedeRepository.findById(idSede);
        if (!optSede.isPresent()) {
            throw new BadRequestException("La sede con ID " + idSede + " no existe.");
        }
        return barberoRepository.findBySede_IdSede(idSede);
    }

    @Override
    public Barbero obtenerBarberoPorId(Integer idBarbero) throws BadRequestException {
        return barberoRepository.findById(idBarbero)
                .orElseThrow(() -> new BadRequestException("Barbero no encontrado con ID: " + idBarbero));
    }

    @Override
    @Transactional
    public RespuestaGenericaRs crearBarbero(BarberoRq barberoRq) throws BadRequestException {
        // Validar campos obligatorios
        if (barberoRq.getNombre() == null || barberoRq.getNombre().trim().isEmpty() ||
            barberoRq.getApellido() == null || barberoRq.getApellido().trim().isEmpty() ||
            barberoRq.getIdSede() == null) {
            throw new BadRequestException("Nombre, apellido e ID de sede son campos obligatorios.");
        }

        // Validar existencia de la sede
        Optional<Sede> optSede = sedeRepository.findById(barberoRq.getIdSede());
        if (!optSede.isPresent()) {
            throw new BadRequestException("La sede con ID " + barberoRq.getIdSede() + " no existe.");
        }

        // Validar duplicidad por nombre y apellido (si se considera único)
        if (barberoRepository.existsByNombreAndApellido(barberoRq.getNombre(), barberoRq.getApellido())) {
            throw new BadRequestException("Ya existe un barbero con el nombre y apellido proporcionados.");
        }

        Barbero barbero = new Barbero();
        barbero.setNombre(barberoRq.getNombre());
        barbero.setApellido(barberoRq.getApellido());
        barbero.setEspecialidad(barberoRq.getEspecialidad());
        barbero.setSede(optSede.get()); // Asigna el objeto Sede completo

        try {
            barberoRepository.save(barbero);
            return new RespuestaGenericaRs(true, "Barbero creado exitosamente.");
        } catch (Exception e) {
            throw new BadRequestException("Error al crear el barbero: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public RespuestaGenericaRs actualizarBarbero(BarberoRq barberoRq) throws BadRequestException {
        // Validar ID del barbero
        if (barberoRq.getIdBarbero() == null) {
            throw new BadRequestException("El ID del barbero es obligatorio para la actualización.");
        }

        // Buscar barbero existente
        Barbero barberoActual = barberoRepository.findById(barberoRq.getIdBarbero())
                .orElseThrow(() -> new BadRequestException("Barbero no encontrado con ID: " + barberoRq.getIdBarbero()));

        // Validar existencia de la nueva sede (si se proporciona)
        Sede nuevaSede = barberoActual.getSede(); // Por defecto, mantiene la sede actual
        if (barberoRq.getIdSede() != null && !Objects.equals(barberoRq.getIdSede(), barberoActual.getSede().getIdSede())) {
            Optional<Sede> optNuevaSede = sedeRepository.findById(barberoRq.getIdSede());
            if (!optNuevaSede.isPresent()) {
                throw new BadRequestException("La nueva sede con ID " + barberoRq.getIdSede() + " no existe.");
            }
            nuevaSede = optNuevaSede.get();
        }

        // Verificar si hay cambios en el nombre/apellido y si el nuevo nombre/apellido ya existe para otro barbero
        boolean nombreCambiado = barberoRq.getNombre() != null && !barberoActual.getNombre().equals(barberoRq.getNombre());
        boolean apellidoCambiado = barberoRq.getApellido() != null && !barberoActual.getApellido().equals(barberoRq.getApellido());

        if (nombreCambiado || apellidoCambiado) {
            if (barberoRepository.findByNombreAndApellido(
                    barberoRq.getNombre() != null ? barberoRq.getNombre() : barberoActual.getNombre(),
                    barberoRq.getApellido() != null ? barberoRq.getApellido() : barberoActual.getApellido()
            ).filter(b -> !b.getIdBarbero().equals(barberoActual.getIdBarbero())).isPresent()) {
                throw new BadRequestException("Ya existe otro barbero con el nombre y apellido proporcionados.");
            }
        }

        // Actualizar campos si se proporcionan en el request
        if (barberoRq.getNombre() != null) {
            barberoActual.setNombre(barberoRq.getNombre());
        }
        if (barberoRq.getApellido() != null) {
            barberoActual.setApellido(barberoRq.getApellido());
        }
        if (barberoRq.getEspecialidad() != null) {
            barberoActual.setEspecialidad(barberoRq.getEspecialidad());
        }
        barberoActual.setSede(nuevaSede); // Asigna la sede (actual o nueva)

        try {
            barberoRepository.save(barberoActual);
            return new RespuestaGenericaRs(true, "Barbero actualizado exitosamente.");
        } catch (Exception e) {
            throw new BadRequestException("Error al actualizar el barbero: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public RespuestaGenericaRs eliminarBarbero(Integer idBarbero) throws BadRequestException {
        if (!barberoRepository.existsById(idBarbero)) {
            throw new BadRequestException("Barbero no encontrado con ID: " + idBarbero);
        }
        try {
            barberoRepository.deleteById(idBarbero);
            return new RespuestaGenericaRs(true, "Barbero eliminado exitosamente.");
        } catch (Exception e) {
            // Aquí podrías manejar ConstraintViolationException si el barbero tiene citas asociadas
            throw new BadRequestException("Error al eliminar el barbero: " + e.getMessage());
        }
    }
}
