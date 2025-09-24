import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Cita, CitaRq } from '../models/cita.interface';
import { RespuestaGenericaRs } from '../models/respuesta-generica.interface';

@Injectable({
  providedIn: 'root'
})
export class CitasService {
  // Ajusta esta URL a la base de tu API para citas.
  private apiUrl = 'http://localhost:8000/barberia/v1/cita'; // <-- ¡Ajusta esta línea si es diferente!

  constructor(private http: HttpClient) { }

  /**
   * Obtiene la lista de todas las citas.
   * @returns Un Observable que emite un array de objetos Cita.
   */
  getCitas(): Observable<Cita[]> {
    return this.http.get<Cita[]>(`${this.apiUrl}/listar`);
  }

  /**
   * Obtiene una cita por su ID.
   * @param idCita El ID de la cita a obtener.
   * @returns Un Observable que emite un objeto Cita.
   */
  getCitaById(idCita: number): Observable<Cita> {
    return this.http.get<Cita>(`${this.apiUrl}/obtener-por-id/${idCita}`);
  }

  /**
   * Crea una nueva cita.
   * @param citaRq El objeto CitaRq a crear.
   * @returns Un Observable que emite una RespuestaGenericaRs.
   */
  createCita(citaRq: CitaRq): Observable<RespuestaGenericaRs> {
    return this.http.post<RespuestaGenericaRs>(`${this.apiUrl}/crear`, citaRq);
  }

  /**
   * Actualiza una cita existente.
   * @param citaRq El objeto CitaRq con los datos actualizados.
   * @returns Un Observable que emite una RespuestaGenericaRs.
   */
  updateCita(citaRq: CitaRq): Observable<RespuestaGenericaRs> {
    return this.http.put<RespuestaGenericaRs>(`${this.apiUrl}/actualizar`, citaRq);
  }

  /**
   * Elimina una cita por su ID.
   * @param idCita El ID de la cita a eliminar.
   * @returns Un Observable que emite una RespuestaGenericaRs.
   */
  deleteCita(idCita: number): Observable<RespuestaGenericaRs> {
    return this.http.delete<RespuestaGenericaRs>(`${this.apiUrl}/eliminar/${idCita}`);
  }

  /**
   * Lista citas por fecha y barbero.
   * @param fecha Fecha de las citas (formato YYYY-MM-DD).
   * @param idBarbero ID del barbero.
   * @returns Un Observable que emite un array de objetos Cita.
   */
  getCitasByFechaAndBarbero(fecha: string, idBarbero: number): Observable<Cita[]> {
    let params = new HttpParams();
    params = params.append('fecha', fecha);
    params = params.append('idBarbero', idBarbero.toString());
    return this.http.get<Cita[]>(`${this.apiUrl}/listar-por-fecha-barbero`, { params: params });
  }

  /**
   * Lista citas por cliente.
   * @param idCliente ID del cliente.
   * @returns Un Observable que emite un array de objetos Cita.
   */
  getCitasByCliente(idCliente: number): Observable<Cita[]> {
    return this.http.get<Cita[]>(`${this.apiUrl}/listar-por-cliente/${idCliente}`);
  }
}