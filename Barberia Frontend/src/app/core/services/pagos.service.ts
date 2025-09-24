import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Pago, PagoRq, MetodoPago } from '../models/pago.interface';
import { RespuestaGenericaRs } from '../models/respuesta-generica.interface';

@Injectable({
  providedIn: 'root'
})
export class PagosService {
  // Ajusta esta URL a la base de tu API para pagos.
  private apiUrl = 'http://localhost:8000/barberia/v1/pago'; // <-- ¡Ajusta esta línea si es diferente!

  constructor(private http: HttpClient) { }

  /**
   * Obtiene la lista de todos los pagos.
   * @returns Un Observable que emite un array de objetos Pago.
   */
  getPagos(): Observable<Pago[]> {
    return this.http.get<Pago[]>(`${this.apiUrl}/listar`);
  }

  /**
   * Obtiene un pago por su ID.
   * @param idPago El ID del pago a obtener.
   * @returns Un Observable que emite un objeto Pago.
   */
  getPagoById(idPago: number): Observable<Pago> {
    return this.http.get<Pago>(`${this.apiUrl}/obtener-por-id/${idPago}`);
  }

  /**
   * Crea un nuevo pago.
   * @param pagoRq El objeto PagoRq a crear.
   * @returns Un Observable que emite una RespuestaGenericaRs.
   */
  createPago(pagoRq: PagoRq): Observable<RespuestaGenericaRs> {
    return this.http.post<RespuestaGenericaRs>(`${this.apiUrl}/crear`, pagoRq);
  }

  /**
   * Actualiza un pago existente.
   * @param pagoRq El objeto PagoRq con los datos actualizados.
   * @returns Un Observable que emite una RespuestaGenericaRs.
   */
  updatePago(pagoRq: PagoRq): Observable<RespuestaGenericaRs> {
    return this.http.put<RespuestaGenericaRs>(`${this.apiUrl}/actualizar`, pagoRq);
  }

  /**
   * Elimina un pago por su ID.
   * @param idPago El ID del pago a eliminar.
   * @returns Un Observable que emite una RespuestaGenericaRs.
   */
  deletePago(idPago: number): Observable<RespuestaGenericaRs> {
    return this.http.delete<RespuestaGenericaRs>(`${this.apiUrl}/eliminar/${idPago}`);
  }

  /**
   * Lista pagos por el ID de la cita asociada.
   * @param idCita ID de la cita.
   * @returns Un Observable que emite un array de objetos Pago.
   */
  getPagosByCita(idCita: number): Observable<Pago[]> {
    return this.http.get<Pago[]>(`${this.apiUrl}/listar-por-cita/${idCita}`);
  }

  /**
   * Lista pagos por método de pago.
   * @param metodoPago Método de pago (Efectivo, Tarjeta, Transferencia).
   * @returns Un Observable que emite un array de objetos Pago.
   */
  getPagosByMetodo(metodoPago: MetodoPago): Observable<Pago[]> {
    let params = new HttpParams();
    params = params.append('metodoPago', metodoPago); // El enum se serializa como string
    return this.http.get<Pago[]>(`${this.apiUrl}/listar-por-metodo`, { params: params });
  }

  /**
   * Lista pagos dentro de un rango de fechas.
   * @param fechaInicio Fecha de inicio del rango (formato yyyy-MM-dd).
   * @param fechaFin Fecha de fin del rango (formato yyyy-MM-dd).
   * @returns Un Observable que emite un array de objetos Pago.
   */
  getPagosByRangoFechas(fechaInicio: string, fechaFin: string): Observable<Pago[]> {
    let params = new HttpParams();
    params = params.append('fechaInicio', fechaInicio);
    params = params.append('fechaFin', fechaFin);
    return this.http.get<Pago[]>(`${this.apiUrl}/listar-por-rango-fechas`, { params: params });
  }
}