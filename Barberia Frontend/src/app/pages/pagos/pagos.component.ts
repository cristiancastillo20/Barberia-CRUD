import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { PagosService } from '../../core/services/pagos.service';
import { CitasService } from '../../core/services/citas.service'; // Para obtener la lista de citas
import { Pago, PagoRq, MetodoPago } from '../../core/models/pago.interface';
import { Cita } from '../../core/models/cita.interface';

declare const bootstrap: any; // Para usar el modal de Bootstrap

@Component({
  selector: 'app-pagos',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './pagos.component.html',
  styleUrl: './pagos.component.scss'
})
export class PagosComponent implements OnInit {
  pagos: Pago[] = [];
  citas: Cita[] = []; // Para el dropdown de citas

  pagoSelected: Pago | null = null;

  modalInstance: any;
  modoFormulario: string = '';
  titleModal: string = '';

  pagoForm!: FormGroup;
  metodosPago = Object.values(MetodoPago); // Para el select de métodos de pago

  constructor(
    private pagosService: PagosService,
    private citasService: CitasService, // Inyecta el servicio de citas
    private formBuilder: FormBuilder
  ) {
    this.cargarFormulario();
  }

  ngOnInit(): void {
    this.cargarListasDesplegables();
    this.cargarListaPagos();
  }

  cargarListasDesplegables(): void {
    // Carga las citas para el dropdown
    this.citasService.getCitas().subscribe({
      next: (data) => this.citas = data,
      error: (err) => console.error('Error al cargar citas para pagos:', err)
    });
  }

  cargarListaPagos() {
    this.pagosService.getPagos().subscribe({
      next: (data: Pago[]) => {
        this.pagos = data;
        console.log('Pagos cargados para la tabla:', this.pagos);
      },
      error: (error) => {
        console.error('Error al obtener los pagos:', error);
        alert('Error al cargar la lista de pagos. Consulte la consola.');
      }
    });
  }

  cargarFormulario() {
    this.pagoForm = this.formBuilder.group({
      idPago: [null],
      monto: [null, [Validators.required, Validators.min(0.01)]], // Monto debe ser positivo
      fechaPago: ['', Validators.required],
      metodoPago: ['', Validators.required],
      idCita: [null, Validators.required]
    });
  }

  openPagoModal(modo: string, pago?: Pago): void {
    this.modoFormulario = modo;
    this.titleModal = modo === 'C' ? 'Registrar Nuevo Pago' : 'Editar Pago';
    this.pagoSelected = pago || null;

    this.pagoForm.reset();
    this.pagoForm.markAsPristine();
    this.pagoForm.markAsUntouched();

    if (modo === 'E' && pago) {
      this.pagoForm.patchValue({
        idPago: pago.idPago,
        monto: pago.monto,
        fechaPago: pago.fechaPago,
        metodoPago: pago.metodoPago,
        idCita: pago.cita.idCita
      });
    } else {
        // Para modo C, la fecha de pago por defecto es hoy
        this.pagoForm.patchValue({
            fechaPago: new Date().toISOString().substring(0, 10)
        });
    }

    const modalElement = document.getElementById('pagoModal');
    if (modalElement) {
      if (!this.modalInstance) {
        this.modalInstance = new bootstrap.Modal(modalElement);
      }
      this.modalInstance.show();
    }
  }

  abrirModoEdicion(pago: Pago) {
    this.openPagoModal('E', pago);
  }

  limpiarFormulario(): void {
    this.pagoForm.reset();
    this.pagoForm.markAsPristine();
    this.pagoForm.markAsUntouched();
    // Reestablecer fecha actual para nuevo pago
    this.pagoForm.patchValue({
        fechaPago: new Date().toISOString().substring(0, 10)
    });
  }

  cerrarModal() {
    this.pagoForm.reset();
    if (this.modalInstance) {
      this.modalInstance.hide();
    }
    this.pagoSelected = null;
    this.cargarListaPagos(); // Recarga la lista al cerrar el modal
  }

  guardarActualizarPago() {
    if (this.pagoForm.valid) {
      const formValue = this.pagoForm.value;

      // Convertir el monto a number para la interfaz
      const monto = parseFloat(formValue.monto);

      // Crear el objeto PagoRq para enviar al backend
      const pagoRq: PagoRq = {
        idPago: formValue.idPago,
        monto: monto,
        fechaPago: formValue.fechaPago,
        metodoPago: formValue.metodoPago, // El enum se mapea directamente
        idCita: formValue.idCita
      };

      if (this.modoFormulario === 'E' && pagoRq.idPago) {
        this.pagosService.updatePago(pagoRq).subscribe({
          next: (response) => {
            console.log('Pago actualizado con éxito:', response);
            alert('Pago actualizado con éxito.');
            this.cerrarModal();
          },
          error: (error) => {
            console.error('Error al actualizar el pago:', error);
            const errorMessage = error.error?.mensaje || 'Hubo un error al actualizar el pago.';
            alert(errorMessage);
          }
        });
      } else if (this.modoFormulario === 'C') {
        this.pagosService.createPago(pagoRq).subscribe({
          next: (response) => {
            console.log('Pago registrado con éxito:', response);
            alert('Pago registrado con éxito.');
            this.cerrarModal();
          },
          error: (error) => {
            console.error('Error al registrar el pago:', error);
            const errorMessage = error.error?.mensaje || 'Hubo un error al registrar el pago.';
            alert(errorMessage);
          }
        });
      }
    } else {
      console.log('Formulario inválido. Revise los campos.');
      alert('Por favor, complete todos los campos requeridos correctamente.');
      this.pagoForm.markAllAsTouched(); // Marca todos los campos como tocados para mostrar errores
    }
  }

  eliminarPago(idPago: number): void {
    if (confirm(`¿Estás seguro de que deseas eliminar el pago con ID ${idPago}?`)) {
      this.pagosService.deletePago(idPago).subscribe({
        next: () => {
          console.log('Pago eliminado exitosamente:', idPago);
          this.cargarListaPagos();
          alert('Pago eliminado exitosamente.');
        },
        error: (error) => {
          console.error('Error al eliminar el pago:', idPago, error);
          const errorMessage = error.error?.mensaje || 'Hubo un error al eliminar el pago.';
          alert(errorMessage);
        }
      });
    }
  }
}