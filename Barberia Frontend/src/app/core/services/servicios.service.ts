// src/app/core/services/servicios.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Servicio } from '../models/servicio.interface';

@Injectable({
  providedIn: 'root'
})
export class ServiciosService {
  // Ajusta esta URL a la base de tu API para servicios.
  // Ejemplo: 'http://localhost:8000/barberia/v1/servicio'
  private apiUrl = 'http://localhost:8000/barberia/v1/servicio'; // <--- ¡Ajusta esta línea si es diferente!

  constructor(private http: HttpClient) { }

  getServicios(): Observable<Servicio[]> {
    return this.http.get<Servicio[]>(`${this.apiUrl}/listar`);
  }

  getServicioById(id: number): Observable<Servicio> {
    return this.http.get<Servicio>(`${this.apiUrl}/obtener-por-id/${id}`);
  }

  createServicio(servicio: Servicio): Observable<Servicio> {
    const { idServicio, ...servicioSinId } = servicio; // Aquí se elimina el ID si el backend lo genera
    return this.http.post<Servicio>(`${this.apiUrl}/crear`, servicioSinId);
  }

  updateServicio(id: number, servicio: Servicio): Observable<Servicio> {
    return this.http.put<Servicio>(`${this.apiUrl}/actualizar`, servicio);
  }

  deleteServicio(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/eliminar/${id}`);
  }
}