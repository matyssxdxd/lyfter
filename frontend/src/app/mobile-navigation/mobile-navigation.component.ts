import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-mobile-navigation',
  standalone: true,
  imports: [CommonModule, RouterModule, MatIconModule],
  templateUrl: './mobile-navigation.component.html',
  styleUrl: './mobile-navigation.component.css'
})
export class MobileNavigationComponent {

}
