
export interface Cliente {
  idCliente?: number; // Es opcional para la creación
  nombre: string;
  apellido: string;
  telefono: string;
  correo?: string; 
  fechaRegistro?: string; 
}