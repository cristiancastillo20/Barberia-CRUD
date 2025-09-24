package com.uniminuto.biblioteca.repository; // Manteniendo el paquete base "biblioteca"

import com.uniminuto.biblioteca.entity.Sede;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad Sede.
 * Proporciona métodos para operaciones CRUD y consultas personalizadas.
 */
@Repository
public interface SedeRepository extends JpaRepository<Sede, Integer> {

    /**
     * Busca una sede por su nombre.
     * @param nombre Nombre de la sede.
     * @return Optional que contiene la sede si se encuentra.
     */
    Optional<Sede> findByNombre(String nombre);

    /**
     * Verifica si existe una sede con un nombre específico.
     * @param nombre Nombre de la sede.
     * @return true si existe, false en caso contrario.
     */
    boolean existsByNombre(String nombre);
}
