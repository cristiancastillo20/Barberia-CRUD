package com.uniminuto.biblioteca.repository; // Manteniendo el paquete base "biblioteca"

import com.uniminuto.biblioteca.entity.Cliente;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad Cliente.
 * Proporciona métodos para operaciones CRUD y consultas personalizadas.
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    /**
     * Busca un cliente por su número de teléfono.
     * @param telefono Teléfono del cliente.
     * @return Optional que contiene el cliente si se encuentra.
     */
    Optional<Cliente> findByTelefono(String telefono);

    /**
     * Verifica si existe un cliente con un número de teléfono específico.
     * @param telefono Teléfono del cliente.
     * @return true si existe, false en caso contrario.
     */
    boolean existsByTelefono(String telefono);

    /**
     * Busca clientes por nombre y apellido.
     * @param nombre Nombre del cliente.
     * @param apellido Apellido del cliente.
     * @return Lista de clientes que coinciden con el nombre y apellido.
     */
    List<Cliente> findByNombreAndApellido(String nombre, String apellido);

    /**
     * Lista todos los clientes ordenados por fecha de registro de forma descendente.
     * @return Lista de clientes ordenados.
     */
    List<Cliente> findAllByOrderByFechaRegistroAsc();
}
