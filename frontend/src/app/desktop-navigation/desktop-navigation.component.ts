import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-desktop-navigation',
  standalone: true,
  imports: [CommonModule, RouterModule, MatIconModule],
  templateUrl: './desktop-navigation.component.html',
  styleUrl: './desktop-navigation.component.css'
})
export class DesktopNavigationComponent {

}
