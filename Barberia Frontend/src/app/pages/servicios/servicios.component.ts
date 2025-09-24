// src/app/pages/servicios/servicios.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { ServiciosService } from '../../core/services/servicios.service';
import { Servicio } from '../../core/models/servicio.interface';
import { Router } from '@angular/router';

declare const bootstrap: any;

@Component({
  selector: 'app-servicios',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './servicios.component.html',
  styleUrl: './servicios.component.scss'
})
export class ServiciosComponent implements OnInit {
  servicios: Servicio[] = [];
  servicioSelected: Servicio | null = null;

  modalInstance: any;
  modoFormulario: string = '';
  titleModal: string = '';

  servicioForm!: FormGroup;

  constructor(
    private serviciosService: ServiciosService,
    private router: Router,
    private formBuilder: FormBuilder
  ) {
    this.cargarFormulario();
  }

  ngOnInit(): void {
    this.cargarListaServicios();
  }

  cargarListaServicios() {
    this.serviciosService.getServicios().subscribe({
      next: (data: Servicio[]) => {
        // Asumiendo que el backend ya los envía ordenados por ID
        // Si no, puedes mantener la línea de ordenamiento aquí:
        // this.servicios = data.sort((a, b) => (a.idServicio ?? 0) - (b.idServicio ?? 0));
        this.servicios = data; // Si el backend ya ordena por ID, simplemente asigna.
        console.log('Servicios cargados para la tabla:', this.servicios);
      },
      error: (error) => {
        console.error('Error al obtener los servicios:', error);
        // Manejo de errores, por ejemplo, mostrar un mensaje al usuario
      }
    });
  }

  cargarFormulario() {
    this.servicioForm = this.formBuilder.group({
      idServicio: [null],
      nombreServicio: ['', Validators.required], // <-- CAMBIO: Usar nombreServicio
      descripcion: ['', Validators.required],
      precio: [null, [Validators.required, Validators.min(0.01)]],
      // ELIMINADO: duracionMinutos: [null, [Validators.required, Validators.min(1)]]
    });
  }

  openServicioModal(modo: string, servicio?: Servicio): void {
    this.modoFormulario = modo;
    this.titleModal = modo === 'C' ? 'Crear Nuevo Servicio' : 'Editar Servicio';
    this.servicioSelected = servicio || null;

    this.servicioForm.reset();
    this.servicioForm.markAsPristine();
    this.servicioForm.markAsUntouched();

    if (modo === 'E' && servicio) {
      // CAMBIO: Parchear con nombreServicio y sin duracionMinutos
      this.servicioForm.patchValue({
        idServicio: servicio.idServicio,
        nombreServicio: servicio.nombreServicio,
        descripcion: servicio.descripcion,
        precio: servicio.precio,
        // ELIMINADO: duracionMinutos: servicio.duracionMinutos
      });
    }

    const modalElement = document.getElementById('servicioModal');
    if (modalElement) {
      if (!this.modalInstance) {
        this.modalInstance = new bootstrap.Modal(modalElement);
      }
      this.modalInstance.show();
    }
  }

  abrirModoEdicion(servicio: Servicio) {
    this.openServicioModal('E', servicio);
  }

  limpiarFormulario(): void {
    this.servicioForm.reset();
    this.servicioForm.markAsPristine();
    this.servicioForm.markAsUntouched();
  }

  cerrarModal() {
    this.servicioForm.reset();
    if (this.modalInstance) {
      this.modalInstance.hide();
    }
    this.servicioSelected = null;
    this.cargarListaServicios(); // Recarga la lista al cerrar el modal
  }

  guardarActualizarServicio() {
    if (this.servicioForm.valid) {
      const servicioData: Servicio = this.servicioForm.value;

      if (this.modoFormulario === 'E' && this.servicioSelected?.idServicio) {
        this.serviciosService.updateServicio(this.servicioSelected.idServicio, servicioData).subscribe({
          next: (response) => {
            console.log('Servicio actualizado con éxito:', response);
            alert('Servicio actualizado con éxito.');
            this.cerrarModal();
          },
          error: (error) => {
            console.error('Error al actualizar el servicio:', error);
            alert('Error al actualizar el servicio. Consulte la consola.');
          }
        });
      } else if (this.modoFormulario === 'C') {
        // Al crear, se envía el objeto sin el idServicio si el backend lo genera
        const { idServicio, ...servicioParaCrear } = servicioData; // Asegúrate de quitar idServicio
        this.serviciosService.createServicio(servicioParaCrear).subscribe({
          next: (response) => {
            console.log('Servicio creado con éxito:', response);
            alert('Servicio creado con éxito.');
            this.cerrarModal();
          },
          error: (error) => {
            console.error('Error al crear el servicio:', error);
            alert('Error al crear el servicio. Consulte la consola.');
          }
        });
      }
    } else {
      console.log('Formulario inválido. Revise los campos.');
      alert('Por favor, complete todos los campos requeridos correctamente.');
      this.servicioForm.markAllAsTouched();
    }
  }

  eliminarServicio(idServicio: number): void {
    if (confirm(`¿Estás seguro de que deseas eliminar el servicio con ID ${idServicio}?`)) {
      this.serviciosService.deleteServicio(idServicio).subscribe({
        next: () => {
          console.log('Servicio eliminado exitosamente:', idServicio);
          this.cargarListaServicios();
        },
        error: (error) => {
          console.error('Error al eliminar el servicio:', idServicio, error);
          alert('Error al eliminar el servicio. Verifique la consola del navegador para más detalles.');
        }
      });
    }
  }
}