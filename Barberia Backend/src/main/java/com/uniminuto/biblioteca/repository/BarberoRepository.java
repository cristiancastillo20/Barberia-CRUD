package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Barbero;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad Barbero.
 * Proporciona métodos para operaciones CRUD y consultas personalizadas.
 */
@Repository
public interface BarberoRepository extends JpaRepository<Barbero, Integer> {

    /**
     * Busca barberos por el ID de la sede a la que pertenecen.
     * @param idSede ID de la sede.
     * @return Lista de barberos.
     */
    List<Barbero> findBySede_IdSede(Integer idSede);

    /**
     * Busca un barbero por su nombre y apellido.
     * Útil para validaciones de duplicidad si se considera la combinación única.
     * @param nombre Nombre del barbero.
     * @param apellido Apellido del barbero.
     * @return Optional que contiene el barbero si se encuentra.
     */
    Optional<Barbero> findByNombreAndApellido(String nombre, String apellido);

    /**
     * Verifica si existe un barbero con un nombre y apellido específicos.
     * @param nombre Nombre del barbero.
     * @param apellido Apellido del barbero.
     * @return true si existe, false en caso contrario.
     */
    boolean existsByNombreAndApellido(String nombre, String apellido);

    /**
     * Lista todos los barberos ordenados por apellido y luego por nombre.
     * @return Lista de barberos ordenados.
     */
    List<Barbero> findAllByOrderByApellidoAscNombreAsc();
}
