package com.uniminuto.biblioteca.repository; // Manteniendo el paquete base "biblioteca"

import com.uniminuto.biblioteca.entity.Pago;
import com.uniminuto.biblioteca.entity.Pago.MetodoPago;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad Pago.
 * Proporciona métodos para operaciones CRUD y consultas personalizadas.
 */
@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer> {

    /**
     * Busca pagos asociados a una cita específica.
     * @param idCita ID de la cita.
     * @return Lista de pagos para la cita.
     */
    List<Pago> findByCita_IdCita(Integer idCita);

    /**
     * Busca pagos por método de pago.
     * @param metodoPago Método de pago.
     * @return Lista de pagos que coinciden con el método.
     */
    List<Pago> findByMetodoPago(MetodoPago metodoPago);

    /**
     * Busca pagos dentro de un rango de fechas.
     * @param fechaInicio Fecha de inicio del rango.
     * @param fechaFin Fecha de fin del rango.
     * @return Lista de pagos dentro del rango de fechas.
     */
    List<Pago> findByFechaPagoBetween(LocalDate fechaInicio, LocalDate fechaFin);
}
