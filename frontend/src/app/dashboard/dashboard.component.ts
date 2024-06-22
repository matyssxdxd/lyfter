import { Component } from '@angular/core';
import { StorageService } from '../_services/storage.service';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { WorkoutLogService } from '../_services/workout-log.service';
import { WorkoutLog } from '../_interfaces/workout-log';
import { WorkoutLogGet } from '../_interfaces/workout-log-get';
import { MatIconModule } from '@angular/material/icon';
import { MobileNavigationComponent } from '../mobile-navigation/mobile-navigation.component';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule, MobileNavigationComponent, MatIconModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css',
})
export class DashboardComponent {
  isLoggedIn: boolean = false;
  selectedDate = new Date();
  errorMessage = "";
  workoutLogs: WorkoutLogGet[] = [];
  userId = -1;

  constructor(private router: Router, private storageService: StorageService, private workoutLogService: WorkoutLogService) {
    this.isLoggedIn = this.storageService.isLoggedIn();

    if (!this.isLoggedIn) {
      this.router.navigate(['/login']);
    }

    this.userId = this.storageService.getUser().id;
  }

  ngOnInit(): void {
    this.workoutLogService.getUserWorkoutLogByUserIdAndDate(this.userId, this.selectedDate).subscribe({
      next: data => {
        this.workoutLogs = data;
        console.log(this.workoutLogs);
      },
      error: err => {
        this.errorMessage = err.error.errorMessage;
      }
    })
  }

  changeDate(option: string): void {
    switch (option) {
      case 'increase':
        this.selectedDate.setDate(this.selectedDate.getDate() + 1);
        this.workoutLogService.getUserWorkoutLogByUserIdAndDate(this.userId, this.selectedDate).subscribe({
          next: data => {
            this.workoutLogs = data;
            console.log(this.workoutLogs);
          },
          error: err => {
            this.workoutLogs = [];
            this.errorMessage = err.error.errorMessage;
          }
        })
        break;
      case 'decrease':
        this.selectedDate.setDate(this.selectedDate.getDate() - 1);
        this.workoutLogService.getUserWorkoutLogByUserIdAndDate(this.userId, this.selectedDate).subscribe({
          next: data => {
            this.workoutLogs = data;
            console.log(this.workoutLogs);
          },
          error: err => {
            this.workoutLogs = [];
            this.errorMessage = err.error.errorMessage;
          }
        })
        break;
      default:
        console.log('how did you get here');
        break;
    }
  }

  calculateTotalVolume(exerciseSets: any[]): number {
    return exerciseSets.map(set => set.reps * set.weight).reduce((a, b) => a + b, 0);
  }

  changeDateFormat(date: Date) {
    return date.toLocaleDateString("en", {
      year: 'numeric',
      month: 'long',
      day: 'numeric',
    });
  }
}
