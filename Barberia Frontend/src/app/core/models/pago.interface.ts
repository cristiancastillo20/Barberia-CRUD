import { Cita } from './cita.interface'; // Asegúrate de que esta interfaz exista


export enum MetodoPago {
  Efectivo = 'Efectivo',
  Tarjeta = 'Tarjeta',
  Transferencia = 'Transferencia'
}

export interface Pago {
  idPago?: number;
  monto: number;       // BigDecimal en Java se mapea a number en TypeScript
  fechaPago: string;   // LocalDate en Java se mapea a string (yyyy-MM-dd)
  metodoPago: MetodoPago;
  cita: Cita;          // Objeto Cita completo
}


export interface PagoRq {
  idPago?: number;
  monto: number;
  fechaPago: string;
  metodoPago: MetodoPago;
  idCita: number;
}