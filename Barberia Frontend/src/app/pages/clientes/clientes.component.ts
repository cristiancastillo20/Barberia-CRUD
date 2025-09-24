import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { ClientesService } from '../../core/services/clientes.service';
import { Cliente } from '../../core/models/cliente.interface';

declare const bootstrap: any; // Para usar el modal de Bootstrap

@Component({
  selector: 'app-clientes',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './clientes.component.html',
  styleUrl: './clientes.component.scss'
})
export class ClientesComponent implements OnInit {
  clientes: Cliente[] = [];
  clienteSelected: Cliente | null = null;

  modalInstance: any;
  modoFormulario: string = '';
  titleModal: string = '';

  clienteForm!: FormGroup;

  constructor(
    private clientesService: ClientesService,
    private formBuilder: FormBuilder
  ) {
    this.cargarFormulario();
  }

  ngOnInit(): void {
    this.cargarListaClientes();
  }

  cargarListaClientes() {
    this.clientesService.getClientes().subscribe({
      next: (data: Cliente[]) => {
        // El backend ya ordena por fechaRegistroDesc, solo asignamos.
        this.clientes = data;
        console.log('Clientes cargados para la tabla:', this.clientes);
      },
      error: (error) => {
        console.error('Error al obtener los clientes:', error);
        // Manejo de errores, por ejemplo, mostrar un mensaje al usuario
        alert('Error al cargar la lista de clientes. Consulte la consola.');
      }
    });
  }

  cargarFormulario() {
    this.clienteForm = this.formBuilder.group({
      idCliente: [null],
      nombre: ['', Validators.required],
      apellido: ['', Validators.required],
      telefono: ['', [Validators.required, Validators.pattern('^[0-9]{7,15}$')]], // Validación de teléfono
      correo: ['', [Validators.email]], // Validación de email, no es obligatorio
      fechaRegistro: [''] // No es obligatorio para el frontend, el backend lo maneja
    });
  }

  openClienteModal(modo: string, cliente?: Cliente): void {
    this.modoFormulario = modo;
    this.titleModal = modo === 'C' ? 'Registrar Nuevo Cliente' : 'Editar Cliente';
    this.clienteSelected = cliente || null;

    this.clienteForm.reset(); // Limpiar el formulario
    this.clienteForm.markAsPristine();
    this.clienteForm.markAsUntouched();

    if (modo === 'E' && cliente) {
      this.clienteForm.patchValue({
        idCliente: cliente.idCliente,
        nombre: cliente.nombre,
        apellido: cliente.apellido,
        telefono: cliente.telefono,
        correo: cliente.correo,
        fechaRegistro: cliente.fechaRegistro // Se carga la fecha si existe
      });
    }

    const modalElement = document.getElementById('clienteModal');
    if (modalElement) {
      if (!this.modalInstance) {
        this.modalInstance = new bootstrap.Modal(modalElement);
      }
      this.modalInstance.show();
    }
  }

  abrirModoEdicion(cliente: Cliente) {
    this.openClienteModal('E', cliente);
  }

  limpiarFormulario(): void {
    this.clienteForm.reset();
    this.clienteForm.markAsPristine();
    this.clienteForm.markAsUntouched();
  }

  cerrarModal() {
    this.clienteForm.reset();
    if (this.modalInstance) {
      this.modalInstance.hide();
    }
    this.clienteSelected = null;
    this.cargarListaClientes(); // Recarga la lista al cerrar el modal
  }

  guardarActualizarCliente() {
    if (this.clienteForm.valid) {
      const formValue = this.clienteForm.value;

      const clienteData: Cliente = {
        idCliente: formValue.idCliente,
        nombre: formValue.nombre,
        apellido: formValue.apellido,
        telefono: formValue.telefono,
        correo: formValue.correo,
        fechaRegistro: formValue.fechaRegistro // Se envía lo que hay en el formulario (o null)
      };

      if (this.modoFormulario === 'E' && clienteData.idCliente) {
        this.clientesService.updateCliente(clienteData).subscribe({
          next: (response) => {
            console.log('Cliente actualizado con éxito:', response);
            alert('Cliente actualizado con éxito.');
            this.cerrarModal();
          },
          error: (error) => {
            console.error('Error al actualizar el cliente:', error);
            const errorMessage = error.error?.mensaje || 'Hubo un error al actualizar el cliente.';
            alert(errorMessage);
          }
        });
      } else if (this.modoFormulario === 'C') {
        // Al crear, se envía el objeto sin el idCliente si el backend lo genera
        this.clientesService.createCliente(clienteData).subscribe({
          next: (response) => {
            console.log('Cliente creado con éxito:', response);
            alert('Cliente creado con éxito.');
            this.cerrarModal();
          },
          error: (error) => {
            console.error('Error al crear el cliente:', error);
            const errorMessage = error.error?.mensaje || 'Hubo un error al crear el cliente.';
            alert(errorMessage);
          }
        });
      }
    } else {
      console.log('Formulario inválido. Revise los campos.');
      alert('Por favor, complete todos los campos requeridos correctamente.');
      this.clienteForm.markAllAsTouched(); // Marca todos los campos como tocados para mostrar errores
    }
  }

  eliminarCliente(idCliente: number): void {
    if (confirm(`¿Estás seguro de que deseas eliminar el cliente con ID ${idCliente}?`)) {
      this.clientesService.deleteCliente(idCliente).subscribe({
        next: () => {
          console.log('Cliente eliminado exitosamente:', idCliente);
          this.cargarListaClientes();
          alert('Cliente eliminado exitosamente.');
        },
        error: (error) => {
          console.error('Error al eliminar el cliente:', idCliente, error);
          const errorMessage = error.error?.mensaje || 'Hubo un error al eliminar el cliente.';
          alert(errorMessage);
        }
      });
    }
  }
}