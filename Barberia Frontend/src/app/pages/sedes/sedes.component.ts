// src/app/pages/sedes/sedes.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms'; // Importa ReactiveFormsModule
import { SedesService } from '../../core/services/sedes.service';
import { Sede } from '../../core/models/sede.interface';
import { Router } from '@angular/router';

// Declara Bootstrap globalmente para poder usarlo
declare const bootstrap: any;

@Component({
  selector: 'app-sedes',
  standalone: true,
  // Asegúrate de importar CommonModule y ReactiveFormsModule
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './sedes.component.html',
  styleUrl: './sedes.component.scss'
})
export class SedesComponent implements OnInit {
  sedes: Sede[] = [];
  sedeSelected: Sede | null = null; // Para almacenar la sede seleccionada en edición

  // Propiedades para el manejo del modal
  modalInstance: any; // Instancia del modal de Bootstrap
  modoFormulario: string = ''; // 'C' para crear, 'E' para editar
  titleModal: string = ''; // Título dinámico del modal

  // FormGroup para el formulario de sedes (usado tanto para crear como para editar)
  sedeForm!: FormGroup;

  constructor(
    private sedesService: SedesService,
    private router: Router,
    private formBuilder: FormBuilder // Inyecta FormBuilder para construir formularios reactivos
    // Si tienes un servicio de mensajes (MessageUtils), inyéctalo aquí:
    // private messageUtils: MessageUtils
  ) {
    this.cargarFormulario(); // Inicializa el formulario al construir el componente
  }

  ngOnInit(): void {
    this.cargarListaSedes(); // Carga la lista de sedes al iniciar el componente
  }

  // Método para cargar la lista de sedes desde el servicio
  cargarListaSedes() {
    this.sedesService.getSedes().subscribe({
      next: (data: Sede[]) => {
        this.sedes = data;
        console.log('Sedes cargadas para la tabla:', this.sedes);
      },
      error: (error) => {
        console.error('Error al obtener las sedes:', error);
        // Si tienes MessageUtils:
        // this.messageUtils.showMessage('Error', error.error.message, 'error');
      }
    });
  }

  // Método para inicializar el FormGroup
  cargarFormulario() {
    this.sedeForm = this.formBuilder.group({
      idSede: [null], // Campo para el ID de la sede (será null para crear, se llenará para editar)
      nombre: ['', Validators.required],
      direccion: ['', Validators.required],
      telefono: ['', [Validators.required, Validators.pattern(/^\d{7,10}$/)]] // Validación de teléfono
    });
  }

  // Método para abrir el modal (CREAR o EDITAR)
  openSedeModal(modo: string, sede?: Sede): void {
    this.modoFormulario = modo;
    this.titleModal = modo === 'C' ? 'Crear Nueva Sede' : 'Editar Sede';
    this.sedeSelected = sede || null; // Guarda la sede si es modo edición

    // Resetea el formulario al abrir el modal para limpiar errores y valores anteriores
    this.sedeForm.reset();
    this.sedeForm.markAsPristine();
    this.sedeForm.markAsUntouched();

    if (modo === 'E' && sede) {
      // Si es modo edición, parchea el formulario con los datos de la sede
      this.sedeForm.patchValue(sede);
    }

    // Obtiene el elemento del modal y muestra la instancia de Bootstrap
    const modalElement = document.getElementById('sedeModal');
    if (modalElement) {
      if (!this.modalInstance) {
        this.modalInstance = new bootstrap.Modal(modalElement);
      }
      this.modalInstance.show();
    }
  }

  // Método auxiliar para abrir el modal en modo edición
  abrirModoEdicion(sede: Sede) {
    this.openSedeModal('E', sede);
  }

  // Método para limpiar el formulario sin cerrar el modal
  limpiarFormulario(): void {
    this.sedeForm.reset();
    this.sedeForm.markAsPristine();
    this.sedeForm.markAsUntouched();
  }

  // Método para cerrar el modal
  cerrarModal() {
    this.sedeForm.reset(); // Resetea el formulario al cerrar el modal
    if (this.modalInstance) {
      this.modalInstance.hide();
    }
    this.sedeSelected = null; // Limpia la sede seleccionada
    this.cargarListaSedes(); // Recarga la lista de sedes para reflejar los cambios
  }

  // Método para guardar o actualizar una sede
  guardarActualizarSede() {
    if (this.sedeForm.valid) {
      const sedeData: Sede = this.sedeForm.value;

      if (this.modoFormulario === 'E' && this.sedeSelected?.idSede) {
        // Es modo edición y tenemos un ID de sede válido
        this.sedesService.updateSede(this.sedeSelected.idSede, sedeData).subscribe({
          next: (response) => {
            console.log('Sede actualizada con éxito:', response);
            alert('Sede actualizada con éxito.'); // Puedes usar MessageUtils aquí
            this.cerrarModal(); // Cierra el modal y recarga la lista
          },
          error: (error) => {
            console.error('Error al actualizar la sede:', error);
            alert('Error al actualizar la sede. Consulte la consola.'); // Puedes usar MessageUtils
          }
        });
      } else if (this.modoFormulario === 'C') {
        // Es modo creación
        this.sedesService.createSede(sedeData).subscribe({
          next: (response) => {
            console.log('Sede creada con éxito:', response);
            alert('Sede creada con éxito.'); // Puedes usar MessageUtils aquí
            this.cerrarModal(); // Cierra el modal y recarga la lista
          },
          error: (error) => {
            console.error('Error al crear la sede:', error);
            alert('Error al crear la sede. Consulte la consola.'); // Puedes usar MessageUtils
          }
        });
      }
    } else {
      console.log('Formulario inválido. Revise los campos.');
      alert('Por favor, complete todos los campos requeridos correctamente.');
      this.sedeForm.markAllAsTouched(); // Muestra los errores de validación
    }
  }

  // Método para eliminar una sede
  eliminarSede(idSede: number): void {
    if (confirm(`¿Estás seguro de que deseas eliminar la sede con ID ${idSede}?`)) {
      this.sedesService.deleteSede(idSede).subscribe({
        next: () => {
          console.log('Sede eliminada exitosamente:', idSede);
          this.cargarListaSedes(); // Recarga la lista después de eliminar
        },
        error: (error) => {
          console.error('Error al eliminar la sede:', idSede, error);
          alert('Error al eliminar la sede. Verifique la consola del navegador para más detalles.');
        }
      });
    }
  }
}