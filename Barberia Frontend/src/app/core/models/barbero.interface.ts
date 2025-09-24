import { Sede } from './sede.interface';

export interface Barbero {
  idBarbero?: number; // Es opcional para la creación
  nombre: string;
  apellido: string;
  especialidad: string;
  sede: Sede;
  idSede?: number; 
}