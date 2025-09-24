import { Routes } from '@angular/router';
import { AdminLayoutComponent } from './core/layout/admin-layout/admin-layout.component';

import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { SedesComponent } from './pages/sedes/sedes.component';
import { ServiciosComponent } from './pages/servicios/servicios.component';
import { EmpleadosComponent } from './pages/empleados/empleados.component';
import { CitasComponent } from './pages/citas/citas.component';
import { ClientesComponent } from './pages/clientes/clientes.component';
import { PagosComponent } from './pages/pagos/pagos.component';

export const routes: Routes = [
  {
    path: '',
    component: AdminLayoutComponent,
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      { path: 'dashboard', component: DashboardComponent },
      { path: 'sedes', component: SedesComponent }, 
      { path: 'servicios', component: ServiciosComponent },
      { path: 'empleados', component: EmpleadosComponent },
      { path: 'citas', component: CitasComponent },
      { path: 'clientes', component: ClientesComponent },
      { path: 'pagos', component: PagosComponent },
    ]
  },
  // { path: '**', component: NotFoundComponent }
];