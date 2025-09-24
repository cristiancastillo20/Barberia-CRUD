// src/app/core/layout/admin-layout/navigation/navigation.ts

// Define la interfaz para un ítem de navegación
export interface NavigationItem {
  id: string;
  title: string;
  type: 'item' | 'collapse' | 'group'; // 'item' para enlaces directos, 'collapse' para submenús, 'group' para secciones
  icon?: string; // Propiedad opcional para la clase del icono (ej. 'fas fa-home')
  url?: string; // Propiedad opcional para la URL si es un 'item'
  children?: NavigationItem[]; // Propiedad opcional para submenús si es un 'collapse' o 'group'
}

// Define la constante que contendrá todos tus ítems de navegación
export const NavigationItems: NavigationItem[] = [
  {
    id: 'general',
    title: 'Inicio', // Nombre del grupo
    type: 'group',
    children: [
      {
        id: 'dashboard',
        title: 'Inicio',
        type: 'item',
        url: '/dashboard', // Asegúrate de que esta URL coincida con tu app.routes.ts
        icon: 'fas fa-chart-line', // Icono para Dashboard
      },
    ]
  },
  {
    id: 'gestion',
    title: 'Gestión', // Otro grupo para las entidades principales de la barbería
    type: 'group',
    children: [
      {
        id: 'sedes',
        title: 'Gestión de Sedes',
        type: 'item',
        url: '/sedes', // URL que usas en app.routes.ts
        icon: 'fas fa-building', // Icono para Sedes
      },
      {
        id: 'servicios',
        title: 'Gestión de Servicios',
        type: 'item',
        url: '/servicios',
        icon: 'fas fa-cut', // Icono de tijeras para Servicios
      },
      {
        id: 'empleados',
        title: 'Gestión de Empleados', // Aquí mapeamos a tu módulo 'barbero'
        type: 'item',
        url: '/empleados',
        icon: 'fas fa-users', // Icono para Empleados
      },
       {
        id: 'clientes',
        title: 'Gestión de Clientes',
        type: 'item',
        url: '/clientes',
        icon: 'fas fa-user-circle', // Icono para Clientes
      },
      {
        id: 'citas',
        title: 'Gestión de Citas',
        type: 'item',
        url: '/citas',
        icon: 'fas fa-calendar-alt', // Icono para Citas
      },
      {
        id: 'pagos',
        title: 'Gestión de Pagos', // Para tu módulo 'pago'
        type: 'item',
        url: '/pagos',
        icon: 'fas fa-money-bill-wave', // Icono para pagos (ej. billete)
      },
      // Aquí puedes añadir más ítems según las necesidades de tu barbería
    ]
  },
];