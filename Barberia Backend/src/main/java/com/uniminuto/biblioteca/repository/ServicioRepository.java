package com.uniminuto.biblioteca.repository; // Manteniendo el paquete base "biblioteca"

import com.uniminuto.biblioteca.entity.Servicio;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad Servicio.
 * Proporciona métodos para operaciones CRUD y consultas personalizadas.
 */
@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Integer> {

    /**
     * Busca un servicio por su nombre.
     * @param nombreServicio Nombre del servicio.
     * @return Optional que contiene el servicio si se encuentra.
     */
    Optional<Servicio> findByNombreServicio(String nombreServicio);

    /**
     * Verifica si existe un servicio con un nombre específico.
     * @param nombreServicio Nombre del servicio.
     * @return true si existe, false en caso contrario.
     */
    boolean existsByNombreServicio(String nombreServicio);

    // --- CAMBIO: Nuevo método para ordenar por ID ---
    /**
     * Lista todos los servicios ordenados por ID de servicio de forma ascendente.
     * @return Lista de servicios ordenados por ID.
     */
    List<Servicio> findAllByOrderByIdServicioAsc();

    // Puedes comentar o eliminar el método anterior si ya no lo necesitas
    // /**
    //  * Lista todos los servicios ordenados por nombre de servicio.
    //  * @return Lista de servicios ordenados.
    //  */
    // List<Servicio> findAllByOrderByNombreServicioAsc();
}