// src/app/core/services/barberos.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Barbero } from '../models/barbero.interface';
import { RespuestaGenericaRs } from '../models/respuesta-generica.interface'; // Asumo que tienes esta interfaz

@Injectable({
  providedIn: 'root'
})
export class BarberosService {
  // Ajusta esta URL a la base de tu API para barberos.
  // Ejemplo: 'http://localhost:8000/barberia/v1/barbero'
  private apiUrl = 'http://localhost:8000/barberia/v1/barbero'; // <-- ¡Ajusta esta línea si es diferente!

  constructor(private http: HttpClient) { }

  /**
   * Obtiene la lista de todos los barberos.
   * @returns Un Observable que emite un array de objetos Barbero.
   */
  getBarberos(): Observable<Barbero[]> {
    return this.http.get<Barbero[]>(`${this.apiUrl}/listar`);
  }

  /**
   * Obtiene un barbero por su ID.
   * @param idBarbero El ID del barbero a obtener.
   * @returns Un Observable que emite un objeto Barbero.
   */
  getBarberoById(idBarbero: number): Observable<Barbero> {
    return this.http.get<Barbero>(`${this.apiUrl}/obtener-por-id/${idBarbero}`);
  }

  /**
   * Obtiene la lista de barberos por ID de sede.
   * @param idSede El ID de la sede.
   * @returns Un Observable que emite un array de objetos Barbero.
   */
  getBarberosPorSede(idSede: number): Observable<Barbero[]> {
    return this.http.get<Barbero[]>(`${this.apiUrl}/listar-por-sede`, { params: { idSede: idSede.toString() } });
  }

  /**
   * Crea un nuevo barbero.
   * @param barbero El objeto Barbero a crear.
   * @returns Un Observable que emite una RespuestaGenericaRs.
   */
  createBarbero(barbero: Barbero): Observable<RespuestaGenericaRs> {
    // Aquí mapeamos el objeto Barbero del frontend al BarberoRq del backend.
    // El backend espera idSede, no el objeto Sede completo para crear/actualizar.
    const barberoRq = {
      idBarbero: barbero.idBarbero, // Se ignorará en la creación, pero se incluye para consistencia
      nombre: barbero.nombre,
      apellido: barbero.apellido,
      especialidad: barbero.especialidad,
      idSede: barbero.sede.idSede // Enviamos solo el ID de la sede
    };
    return this.http.post<RespuestaGenericaRs>(`${this.apiUrl}/crear`, barberoRq);
  }

  /**
   * Actualiza un barbero existente.
   * @param barbero El objeto Barbero con los datos actualizados.
   * @returns Un Observable que emite una RespuestaGenericaRs.
   */
  updateBarbero(barbero: Barbero): Observable<RespuestaGenericaRs> {
     // Aquí mapeamos el objeto Barbero del frontend al BarberoRq del backend.
    // El backend espera idSede, no el objeto Sede completo para crear/actualizar.
    const barberoRq = {
      idBarbero: barbero.idBarbero,
      nombre: barbero.nombre,
      apellido: barbero.apellido,
      especialidad: barbero.especialidad,
      idSede: barbero.sede.idSede // Enviamos solo el ID de la sede
    };
    return this.http.put<RespuestaGenericaRs>(`${this.apiUrl}/actualizar`, barberoRq);
  }

  /**
   * Elimina un barbero por su ID.
   * @param idBarbero El ID del barbero a eliminar.
   * @returns Un Observable que emite una RespuestaGenericaRs.
   */
  deleteBarbero(idBarbero: number): Observable<RespuestaGenericaRs> {
    return this.http.delete<RespuestaGenericaRs>(`${this.apiUrl}/eliminar/${idBarbero}`);
  }
}