import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { ExerciseService } from '../_services/exercise.service';
import { WorkoutService } from '../_services/workout.service';
import { Exercise } from '../_interfaces/exercise';
import { FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { StorageService } from '../_services/storage.service';
import { Router } from '@angular/router';
import { MobileNavigationComponent } from '../mobile-navigation/mobile-navigation.component';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-new-workout',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, MobileNavigationComponent, MatIconModule],
  templateUrl: './new-workout.component.html',
  styleUrls: ['./new-workout.component.css']
})
export class NewWorkoutComponent {
  isLoggedIn = false;
  form: FormGroup;
  exercises: Exercise[] = [];
  errorMessage = "";

  constructor(private exerciseService: ExerciseService, private workoutService: WorkoutService, private storageService: StorageService, private router: Router, private fb: FormBuilder) {
    this.isLoggedIn = this.storageService.isLoggedIn();

    if (!this.isLoggedIn) {
      this.router.navigate(["/login"]);
    }

    this.form = this.fb.group({
      userId: this.fb.control(this.storageService.getUser().id),
      name: this.fb.control("", Validators.required),
      description: this.fb.control("", Validators.required),
      exercises: this.fb.array([])
    });
  }

  ngOnInit(): void {

    this.exerciseService.getAllExercises().subscribe({
      next: data => {
        this.exercises = data;
      },
      error: err => {
        this.errorMessage = err.error.errorMessage;
      }
    });
  }

  toggleExercise(exerciseId: number) {
    const exercisesFormArray = this.form.get('exercises') as FormArray;

    const exerciseIndex = exercisesFormArray.controls.findIndex(control => control.value === exerciseId);

    if (exerciseIndex === -1) {
      exercisesFormArray.push(this.fb.control(exerciseId));
    } else {
      exercisesFormArray.removeAt(exerciseIndex);
      console.warn(`Exercise with id ${exerciseId} has been removed.`);
    }
  }

  isExerciseInFormArray(exerciseId: number): boolean {
    const exercisesFormArray = this.form.get('exercises') as FormArray;
    return exercisesFormArray.controls.some(control => control.value === exerciseId);
  }

  createWorkout() {
    if (this.form.valid) {
      console.log(this.form.value);
      this.workoutService.createWorkout(this.form.value).subscribe({
        next: data => {
          console.log(data);
        },
        error: err => {
          this.errorMessage = err.error.errorMessage;
        }
      })
    }
  }
}
