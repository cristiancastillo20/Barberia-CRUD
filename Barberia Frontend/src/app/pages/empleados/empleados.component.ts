// src/app/pages/empleados/empleados.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { BarberosService } from '../../core/services/barberos.service';
import { SedesService } from '../../core/services/sedes.service'; // Para el dropdown de sedes
import { Barbero } from '../../core/models/barbero.interface';
import { Sede } from '../../core/models/sede.interface'; // Para tipar las sedes

declare const bootstrap: any; // Para usar el modal de Bootstrap

@Component({
  selector: 'app-empleados',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './empleados.component.html',
  styleUrl: './empleados.component.scss'
})
export class EmpleadosComponent implements OnInit {
  empleados: Barbero[] = [];
  sedes: Sede[] = []; // Para el dropdown de sedes
  empleadoSelected: Barbero | null = null;

  modalInstance: any;
  modoFormulario: string = '';
  titleModal: string = '';

  empleadoForm!: FormGroup;

  constructor(
    private barberosService: BarberosService,
    private sedesService: SedesService, // Inyectar el servicio de sedes
    private formBuilder: FormBuilder
  ) {
    this.cargarFormulario();
  }

  ngOnInit(): void {
    this.cargarListaEmpleados();
    this.cargarSedes(); // Cargar las sedes al iniciar el componente
  }

  cargarListaEmpleados() {
    this.barberosService.getBarberos().subscribe({
      next: (data: Barbero[]) => {
        // El backend ya ordena por apellido y nombre, así que solo asignamos.
        this.empleados = data;
        console.log('Empleados cargados para la tabla:', this.empleados);
      },
      error: (error) => {
        console.error('Error al obtener los empleados:', error);
        // Manejo de errores, por ejemplo, mostrar un mensaje al usuario
      }
    });
  }

  cargarSedes() {
    this.sedesService.getSedes().subscribe({
      next: (data: Sede[]) => {
        this.sedes = data;
        console.log('Sedes cargadas para el dropdown:', this.sedes);
      },
      error: (error) => {
        console.error('Error al cargar las sedes:', error);
      }
    });
  }

  cargarFormulario() {
    this.empleadoForm = this.formBuilder.group({
      idBarbero: [null],
      nombre: ['', Validators.required],
      apellido: ['', Validators.required],
      especialidad: [''], // La especialidad no es obligatoria según tu backend
      idSede: [null, Validators.required] // Usamos idSede para el formulario
    });
  }

  openEmpleadoModal(modo: string, empleado?: Barbero): void {
    this.modoFormulario = modo;
    this.titleModal = modo === 'C' ? 'Crear Nuevo Empleado' : 'Editar Empleado';
    this.empleadoSelected = empleado || null;

    this.empleadoForm.reset();
    this.empleadoForm.markAsPristine();
    this.empleadoForm.markAsUntouched();

    if (modo === 'E' && empleado) {
      this.empleadoForm.patchValue({
        idBarbero: empleado.idBarbero,
        nombre: empleado.nombre,
        apellido: empleado.apellido,
        especialidad: empleado.especialidad,
        idSede: empleado.sede?.idSede // Usar idSede del objeto Sede anidado
      });
    }

    const modalElement = document.getElementById('empleadoModal');
    if (modalElement) {
      if (!this.modalInstance) {
        this.modalInstance = new bootstrap.Modal(modalElement);
      }
      this.modalInstance.show();
    }
  }

  abrirModoEdicion(empleado: Barbero) {
    this.openEmpleadoModal('E', empleado);
  }

  limpiarFormulario(): void {
    this.empleadoForm.reset();
    this.empleadoForm.markAsPristine();
    this.empleadoForm.markAsUntouched();
  }

  cerrarModal() {
    this.empleadoForm.reset();
    if (this.modalInstance) {
      this.modalInstance.hide();
    }
    this.empleadoSelected = null;
    this.cargarListaEmpleados(); // Recarga la lista al cerrar el modal
  }

  guardarActualizarEmpleado() {
    if (this.empleadoForm.valid) {
      const formValue = this.empleadoForm.value;

      // Construimos el objeto Barbero para el servicio
      // Asignamos el objeto Sede completo para que coincida con la interfaz Barbero,
      // aunque el servicio enviará solo el idSede en el Rq
      const empleadoData: Barbero = {
        idBarbero: formValue.idBarbero,
        nombre: formValue.nombre,
        apellido: formValue.apellido,
        especialidad: formValue.especialidad,
        sede: this.sedes.find(s => s.idSede === formValue.idSede) || {} as Sede // Asegurar que sede no sea null
      };

      if (this.modoFormulario === 'E' && empleadoData.idBarbero) {
        this.barberosService.updateBarbero(empleadoData).subscribe({
          next: (response) => {
            console.log('Empleado actualizado con éxito:', response);
            alert('Empleado actualizado con éxito.');
            this.cerrarModal();
          },
          error: (error) => {
            console.error('Error al actualizar el empleado:', error);
            alert('Error al actualizar el empleado. Consulte la consola.');
          }
        });
      } else if (this.modoFormulario === 'C') {
        // Al crear, se envía el objeto sin el idBarbero si el backend lo genera
        this.barberosService.createBarbero(empleadoData).subscribe({
          next: (response) => {
            console.log('Empleado creado con éxito:', response);
            alert('Empleado creado con éxito.');
            this.cerrarModal();
          },
          error: (error) => {
            console.error('Error al crear el empleado:', error);
            alert('Error al crear el empleado. Consulte la consola.');
          }
        });
      }
    } else {
      console.log('Formulario inválido. Revise los campos.');
      alert('Por favor, complete todos los campos requeridos correctamente.');
      this.empleadoForm.markAllAsTouched();
    }
  }

  eliminarEmpleado(idBarbero: number): void {
    if (confirm(`¿Estás seguro de que deseas eliminar el empleado con ID ${idBarbero}?`)) {
      this.barberosService.deleteBarbero(idBarbero).subscribe({
        next: () => {
          console.log('Empleado eliminado exitosamente:', idBarbero);
          this.cargarListaEmpleados();
          alert('Empleado eliminado exitosamente.');
        },
        error: (error) => {
          console.error('Error al eliminar el empleado:', idBarbero, error);
          alert('Error al eliminar el empleado. Verifique la consola del navegador para más detalles.');
        }
      });
    }
  }
}