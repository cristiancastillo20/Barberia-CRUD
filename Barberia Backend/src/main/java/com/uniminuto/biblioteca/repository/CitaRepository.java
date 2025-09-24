package com.uniminuto.biblioteca.repository; // Manteniendo el paquete base "biblioteca"

import com.uniminuto.biblioteca.entity.Cita;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad Cita.
 * Proporciona métodos para operaciones CRUD y consultas personalizadas.
 */
@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer> {

    /**
     * Busca citas por fecha y barbero.
     * @param fecha Fecha de la cita.
     * @param idBarbero ID del barbero.
     * @return Lista de citas para esa fecha y barbero.
     */
    List<Cita> findByFechaAndBarbero_IdBarberoOrderByHoraAsc(LocalDate fecha, Integer idBarbero);

    /**
     * Busca una cita específica por fecha, hora y barbero.
     * Útil para validar la disponibilidad del barbero.
     * @param fecha Fecha de la cita.
     * @param hora Hora de la cita.
     * @param idBarbero ID del barbero.
     * @return Optional que contiene la cita si se encuentra.
     */
    Optional<Cita> findByFechaAndHoraAndBarbero_IdBarbero(LocalDate fecha, LocalTime hora, Integer idBarbero);

    /**
     * Lista todas las citas ordenadas por fecha y hora.
     * @return Lista de citas ordenadas.
     */
    List<Cita> findAllByOrderByFechaAscHoraAsc();

    /**
     * Busca citas por cliente.
     * @param idCliente ID del cliente.
     * @return Lista de citas del cliente.
     */
    List<Cita> findByCliente_IdClienteOrderByFechaDescHoraDesc(Integer idCliente);
}
