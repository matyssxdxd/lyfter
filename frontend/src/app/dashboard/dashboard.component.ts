import { Component } from '@angular/core';
import { StorageService } from '../_services/storage.service';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css',
})
export class DashboardComponent {
  isLoggedIn: boolean = false;
  selectedDate = new Date();

  constructor(private router: Router, private storageService: StorageService) {
    this.isLoggedIn = this.storageService.isLoggedIn();

    if (!this.isLoggedIn) {
      this.router.navigate(['/login']);
    }
  }

  ngOnInit(): void {}

  changeDate(option: string): void {
    switch (option) {
      case 'increase':
        this.selectedDate.setDate(this.selectedDate.getDate() + 1);
        break;
      case 'decrease':
        this.selectedDate.setDate(this.selectedDate.getDate() - 1);
        break;
      default:
        console.log('how did you get here');
        break;
    }
  }
}
