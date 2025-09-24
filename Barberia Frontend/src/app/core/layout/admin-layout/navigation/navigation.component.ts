// src/app/core/layout/admin-layout/navigation/navigation.component.ts
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { NavigationItem, NavigationItems } from './navigation'; // Asegúrate de que 'navigation' apunte al archivo correcto

@Component({
  selector: 'app-navigation',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './navigation.component.html',
  styleUrl: './navigation.component.scss'
})
export class NavigationComponent {
  readonly navigationItems = NavigationItems;
  constructor() { }
}