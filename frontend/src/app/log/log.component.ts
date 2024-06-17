import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { StorageService } from '../_services/storage.service';
import { CommonModule } from '@angular/common';
import { FormGroup, FormBuilder, FormArray, Validators, ReactiveFormsModule } from '@angular/forms';
import { WorkoutService } from '../_services/workout.service';
import { Workout } from '../_interfaces/workout';
import { Exercise } from '../_interfaces/exercise';

@Component({
  selector: 'app-log',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './log.component.html',
  styleUrls: ['./log.component.css']
})
export class LogComponent {
  isWorkoutSelected = false;
  isLoggedIn: boolean = false;
  form: FormGroup;
  currentExerciseIndex: number = 0;
  exercises: Exercise[] = []; // Get these from workout
  workouts: Workout[] = [];
  errorMessage: string = "";

  constructor(
    private router: Router,
    private storageService: StorageService,
    private workoutService: WorkoutService,
    private fb: FormBuilder
  ) {
    this.form = this.fb.group({
      exercises: this.fb.array([])
    });
  }

  ngOnInit(): void {
    this.isLoggedIn = this.storageService.isLoggedIn();

    if (!this.isLoggedIn) {
      this.router.navigate(["/login"]);
    }

    const userId = this.storageService.getUser().id;

    this.workoutService.getUserWorkouts(userId).subscribe({
      next: data => {
        this.workouts = data;
      },
      error: err => {
        this.errorMessage = err.error.errorMessage;
      }
    });
  }

  selectWorkout(id: number) {
    console.log(id);
    this.isWorkoutSelected = true;
    const selectedWorkout = this.workouts.find(workout => workout.id === id);
    if (selectedWorkout) {
      this.exercises = selectedWorkout.exercises;
      this.setExercisesForm();
    } else {
      this.exercises = [];
    }
  }

  setExercisesForm() {
    const exercisesFormArray = this.form.get('exercises') as FormArray;
    this.exercises.forEach(exercise => {
      const exerciseGroup = this.fb.group({
        name: [exercise.name],
        sets: this.fb.array([])
      });
      exercisesFormArray.push(exerciseGroup);
    });
  }

  get exercisesFormArray(): FormArray {
    return this.form.get('exercises') as FormArray;
  }

  setsFormArray(index: number): FormArray {
    return this.exercisesFormArray.at(index).get('sets') as FormArray;
  }

  addSet(exerciseIndex: number) {
    this.setsFormArray(exerciseIndex).push(this.fb.group({
      weight: ['', Validators.required],
      reps: ['', Validators.required]
    }));
  }

  removeSet(exerciseIndex: number, setIndex: number) {
    this.setsFormArray(exerciseIndex).removeAt(setIndex);
  }

  logWorkout() {
    if (this.form.valid) {
      console.log(this.form.value);
    }
  }
}
