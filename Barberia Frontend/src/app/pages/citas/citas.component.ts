import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CitasService } from '../../core/services/citas.service';
import { ClientesService } from '../../core/services/clientes.service';
import { BarberosService } from '../../core/services/barberos.service';
import { ServiciosService } from '../../core/services/servicios.service';
import { Cita, CitaRq } from '../../core/models/cita.interface';
import { Cliente } from '../../core/models/cliente.interface';
import { Barbero } from '../../core/models/barbero.interface';
import { Servicio } from '../../core/models/servicio.interface';

declare const bootstrap: any; // Para usar el modal de Bootstrap

@Component({
  selector: 'app-citas',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './citas.component.html',
  styleUrl: './citas.component.scss'
})
export class CitasComponent implements OnInit {
  citas: Cita[] = [];
  clientes: Cliente[] = [];
  barberos: Barbero[] = [];
  servicios: Servicio[] = [];

  citaSelected: Cita | null = null;

  modalInstance: any;
  modoFormulario: string = '';
  titleModal: string = '';

  citaForm!: FormGroup;

  constructor(
    private citasService: CitasService,
    private clientesService: ClientesService,
    private barberosService: BarberosService,
    private serviciosService: ServiciosService,
    private formBuilder: FormBuilder
  ) {
    this.cargarFormulario();
  }

  ngOnInit(): void {
    this.cargarListasDesplegables();
    this.cargarListaCitas();
  }

  cargarListasDesplegables(): void {
    this.clientesService.getClientes().subscribe({
      next: (data) => this.clientes = data,
      error: (err) => console.error('Error al cargar clientes:', err)
    });
    this.barberosService.getBarberos().subscribe({
      next: (data) => this.barberos = data,
      error: (err) => console.error('Error al cargar barberos:', err)
    });
    this.serviciosService.getServicios().subscribe({
      next: (data) => this.servicios = data,
      error: (err) => console.error('Error al cargar servicios:', err)
    });
  }

  cargarListaCitas() {
    this.citasService.getCitas().subscribe({
      next: (data: Cita[]) => {
        this.citas = data;
        console.log('Citas cargadas para la tabla:', this.citas);
      },
      error: (error) => {
        console.error('Error al obtener las citas:', error);
        alert('Error al cargar la lista de citas. Consulte la consola.');
      }
    });
  }

  cargarFormulario() {
    this.citaForm = this.formBuilder.group({
      idCita: [null],
      fecha: ['', Validators.required],
      hora: ['', Validators.required],
      idCliente: [null, Validators.required],
      idBarbero: [null, Validators.required],
      idServicio: [null, Validators.required]
    });
  }

  openCitaModal(modo: string, cita?: Cita): void {
    this.modoFormulario = modo;
    this.titleModal = modo === 'C' ? 'Agendar Nueva Cita' : 'Editar Cita';
    this.citaSelected = cita || null;

    this.citaForm.reset();
    this.citaForm.markAsPristine();
    this.citaForm.markAsUntouched();

    if (modo === 'E' && cita) {
      this.citaForm.patchValue({
        idCita: cita.idCita,
        fecha: cita.fecha,
        hora: cita.hora,
        idCliente: cita.cliente.idCliente,
        idBarbero: cita.barbero.idBarbero,
        idServicio: cita.servicio.idServicio
      });
    }

    const modalElement = document.getElementById('citaModal');
    if (modalElement) {
      if (!this.modalInstance) {
        this.modalInstance = new bootstrap.Modal(modalElement);
      }
      this.modalInstance.show();
    }
  }

  abrirModoEdicion(cita: Cita) {
    this.openCitaModal('E', cita);
  }

  limpiarFormulario(): void {
    this.citaForm.reset();
    this.citaForm.markAsPristine();
    this.citaForm.markAsUntouched();
  }

  cerrarModal() {
    this.citaForm.reset();
    if (this.modalInstance) {
      this.modalInstance.hide();
    }
    this.citaSelected = null;
    this.cargarListaCitas(); // Recarga la lista al cerrar el modal
  }

  guardarActualizarCita() {
    if (this.citaForm.valid) {
      const formValue = this.citaForm.value;

      // Crear el objeto CitaRq para enviar al backend
      const citaRq: CitaRq = {
        idCita: formValue.idCita,
        fecha: formValue.fecha,
        hora: formValue.hora,
        idCliente: formValue.idCliente,
        idBarbero: formValue.idBarbero,
        idServicio: formValue.idServicio
      };

      if (this.modoFormulario === 'E' && citaRq.idCita) {
        this.citasService.updateCita(citaRq).subscribe({
          next: (response) => {
            console.log('Cita actualizada con éxito:', response);
            alert('Cita actualizada con éxito.');
            this.cerrarModal();
          },
          error: (error) => {
            console.error('Error al actualizar la cita:', error);
            const errorMessage = error.error?.mensaje || 'Hubo un error al actualizar la cita.';
            alert(errorMessage);
          }
        });
      } else if (this.modoFormulario === 'C') {
        this.citasService.createCita(citaRq).subscribe({
          next: (response) => {
            console.log('Cita agendada con éxito:', response);
            alert('Cita agendada con éxito.');
            this.cerrarModal();
          },
          error: (error) => {
            console.error('Error al agendar la cita:', error);
            const errorMessage = error.error?.mensaje || 'Hubo un error al agendar la cita.';
            alert(errorMessage);
          }
        });
      }
    } else {
      console.log('Formulario inválido. Revise los campos.');
      alert('Por favor, complete todos los campos requeridos correctamente.');
      this.citaForm.markAllAsTouched(); // Marca todos los campos como tocados para mostrar errores
    }
  }

  eliminarCita(idCita: number): void {
    if (confirm(`¿Estás seguro de que deseas eliminar la cita con ID ${idCita}?`)) {
      this.citasService.deleteCita(idCita).subscribe({
        next: () => {
          console.log('Cita eliminada exitosamente:', idCita);
          this.cargarListaCitas();
          alert('Cita eliminada exitosamente.');
        },
        error: (error) => {
          console.error('Error al eliminar la cita:', idCita, error);
          const errorMessage = error.error?.mensaje || 'Hubo un error al eliminar la cita.';
          alert(errorMessage);
        }
      });
    }
  }
}