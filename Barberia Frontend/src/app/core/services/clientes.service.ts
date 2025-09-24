import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Cliente } from '../models/cliente.interface';
import { RespuestaGenericaRs } from '../models/respuesta-generica.interface';

@Injectable({
  providedIn: 'root'
})
export class ClientesService {
  // Ajusta esta URL a la base de tu API para clientes.
  // Ejemplo: 'http://localhost:8000/barberia/v1/cliente'
  private apiUrl = 'http://localhost:8000/barberia/v1/cliente'; // <-- ¡Ajusta esta línea si es diferente!

  constructor(private http: HttpClient) { }

  /**
   * Obtiene la lista de todos los clientes.
   * @returns Un Observable que emite un array de objetos Cliente.
   */
  getClientes(): Observable<Cliente[]> {
    return this.http.get<Cliente[]>(`${this.apiUrl}/listar`);
  }

  /**
   * Obtiene un cliente por su ID.
   * @param idCliente El ID del cliente a obtener.
   * @returns Un Observable que emite un objeto Cliente.
   */
  getClienteById(idCliente: number): Observable<Cliente> {
    return this.http.get<Cliente>(`${this.apiUrl}/obtener-por-id/${idCliente}`);
  }

  /**
   * Crea un nuevo cliente.
   * @param cliente El objeto Cliente a crear.
   * @returns Un Observable que emite una RespuestaGenericaRs.
   */
  createCliente(cliente: Cliente): Observable<RespuestaGenericaRs> {
    // Si fechaRegistro no se envía desde el frontend, el backend usará LocalDate.now()
    // Aseguramos que el objeto enviado coincida con ClienteRq del backend
    const clienteRq = {
      idCliente: cliente.idCliente, // Se ignorará en la creación, pero se incluye para consistencia
      nombre: cliente.nombre,
      apellido: cliente.apellido,
      telefono: cliente.telefono,
      correo: cliente.correo,
      fechaRegistro: cliente.fechaRegistro // Puede ser null, y el backend lo manejará
    };
    return this.http.post<RespuestaGenericaRs>(`${this.apiUrl}/crear`, clienteRq);
  }

  /**
   * Actualiza un cliente existente.
   * @param cliente El objeto Cliente con los datos actualizados.
   * @returns Un Observable que emite una RespuestaGenericaRs.
   */
  updateCliente(cliente: Cliente): Observable<RespuestaGenericaRs> {
    const clienteRq = {
      idCliente: cliente.idCliente,
      nombre: cliente.nombre,
      apellido: cliente.apellido,
      telefono: cliente.telefono,
      correo: cliente.correo,
      fechaRegistro: cliente.fechaRegistro // Puede ser null o el valor existente
    };
    return this.http.put<RespuestaGenericaRs>(`${this.apiUrl}/actualizar`, clienteRq);
  }

  /**
   * Elimina un cliente por su ID.
   * @param idCliente El ID del cliente a eliminar.
   * @returns Un Observable que emite una RespuestaGenericaRs.
   */
  deleteCliente(idCliente: number): Observable<RespuestaGenericaRs> {
    return this.http.delete<RespuestaGenericaRs>(`${this.apiUrl}/eliminar/${idCliente}`);
  }
}