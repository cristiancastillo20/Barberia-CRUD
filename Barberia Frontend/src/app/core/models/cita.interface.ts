import { Cliente } from './cliente.interface';
import { Barbero } from './barbero.interface';
import { Servicio } from './servicio.interface'; 

export interface Cita {
  idCita?: number;
  fecha: string; // Para LocalDate (yyyy-MM-dd)
  hora: string;  // Para LocalTime (HH:mm:ss o HH:mm)
  cliente: Cliente; // Objeto Cliente completo
  barbero: Barbero; // Objeto Barbero completo
  servicio: Servicio; // Objeto Servicio completo
}


export interface CitaRq {
  idCita?: number;
  fecha: string;
  hora: string;
  idCliente: number;
  idBarbero: number;
  idServicio: number;
}