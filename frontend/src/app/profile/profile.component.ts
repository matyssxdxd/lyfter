import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { StorageService } from '../_services/storage.service';
import { MobileNavigationComponent } from '../mobile-navigation/mobile-navigation.component';
import { AuthService } from '../_services/auth.service';
import { Router } from '@angular/router';
import { DesktopNavigationComponent } from '../desktop-navigation/desktop-navigation.component';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, MobileNavigationComponent, DesktopNavigationComponent],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent {
  currentUser: any;
  isLoggedIn = false;

  constructor(private storageService: StorageService, private authService: AuthService, private router: Router) {
    this.isLoggedIn = this.storageService.isLoggedIn();

    if (!this.isLoggedIn) {
      this.router.navigate(["/login"]);
    }
  }

  ngOnInit(): void {
    this.currentUser = this.storageService.getUser();
  }

  logout(): void {
    this.authService.logout().subscribe({
      next: res => {
        console.log(res);
        this.storageService.clean();

        this.router.navigate(['/home']);
      },
      error: err => {
        console.log(err.error.message);
      }
    })
  }
}
