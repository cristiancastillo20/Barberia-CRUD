// src/app/core/services/sedes.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Sede } from '../models/sede.interface';

@Injectable({
  providedIn: 'root'
})
export class SedesService {

  // Esta es la BASE de la URL de tu API para SEDES
  // Coincide con server.servlet.contextPath + @RequestMapping("/sede") en tu Spring Boot
  private apiUrl = 'http://localhost:8000/barberia/v1/sede';

  constructor(private http: HttpClient) { }

  /**
   * Obtiene todas las sedes del backend.
   * La URL completa será: http://localhost:8000/barberia/v1/sede/listar
   * @returns Un Observable con un arreglo de objetos Sede.
   */
  getSedes(): Observable<Sede[]> {
    return this.http.get<Sede[]>(`${this.apiUrl}/listar`); // Agrega el sufijo "/listar"
  }

  /**
   * Obtiene una sede por su ID.
   * La URL completa será: http://localhost:8000/barberia/v1/sede/obtener-por-id/{id}
   * @param id El ID de la sede a obtener.
   * @returns Un Observable con el objeto Sede.
   */
  getSedeById(id: number): Observable<Sede> {
    return this.http.get<Sede>(`${this.apiUrl}/obtener-por-id/${id}`);
  }

  /**
   * Crea una nueva sede en el backend.
   * La URL completa será: http://localhost:8000/barberia/v1/sede/crear
   * @param sede Los datos de la nueva sede.
   * @returns Un Observable con el objeto Sede creado.
   */
  createSede(sede: Sede): Observable<Sede> {
    return this.http.post<Sede>(`${this.apiUrl}/crear`, sede);
  }

  /**
   * Actualiza una sede existente en el backend.
   * La URL completa será: http://localhost:8000/barberia/v1/sede/actualizar
   * @param id El ID de la sede a actualizar (el SedeRq enviado debe incluir este ID).
   * @param sede Los nuevos datos de la sede.
   * @returns Un Observable con el objeto Sede actualizado.
   */
  updateSede(id: number, sede: Sede): Observable<Sede> {
    // Tu API de Spring Boot espera el ID dentro del SedeRq para actualizar.
    // Aunque el 'id' del parámetro no se usa directamente en la URL, se mantiene por el contexto.
    // Es crucial que el 'sede' (SedeRq) que pasas contenga el 'idSede' correcto.
    return this.http.put<Sede>(`${this.apiUrl}/actualizar`, sede);
  }

  /**
   * Elimina una sede del backend.
   * La URL completa será: http://localhost:8000/barberia/v1/sede/eliminar/{id}
   * @param id El ID de la sede a eliminar.
   * @returns Un Observable vacío cuando la eliminación es exitosa.
   */
  deleteSede(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/eliminar/${id}`);
  }
}