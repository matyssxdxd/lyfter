import { Component, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { StorageService } from '../_services/storage.service';
import { CommonModule } from '@angular/common';
import { FormGroup, FormBuilder, FormArray, Validators, ReactiveFormsModule, FormControl } from '@angular/forms';
import { WorkoutService } from '../_services/workout.service';
import { Workout } from '../_interfaces/workout';
import { Exercise } from '../_interfaces/exercise';
import { WorkoutLogService } from '../_services/workout-log.service';

@Component({
  selector: 'app-log',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './log.component.html',
  styleUrls: ['./log.component.css']
})
export class LogComponent implements OnDestroy {
  isWorkoutSelected = false;
  isLoggedIn: boolean = false;
  form: FormGroup;
  currentExerciseIndex: number = 0;
  exercises: Exercise[] = []; // Get these from workout
  workouts: Workout[] = [];
  errorMessage: string = "";
  incrementLengthIntervalId: any;
  length = 0;

  constructor(
    private router: Router,
    private storageService: StorageService,
    private workoutService: WorkoutService,
    private workoutLogService: WorkoutLogService,
    private fb: FormBuilder
  ) {
    this.isLoggedIn = this.storageService.isLoggedIn();

    if (!this.isLoggedIn) {
      this.router.navigate(["/login"]);
    }
    
    this.form = this.fb.group({
      workoutId: this.fb.control(null),
      userId: this.storageService.getUser().id,
      exercises: this.fb.array([]),
      length: this.fb.control('')
    });
  }

  ngOnInit(): void {
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
      this.form.controls['workoutId'].setValue(id);
      this.setExercisesForm();
      if (this.incrementLengthIntervalId) {
        clearInterval(this.incrementLengthIntervalId);
      }
      this.incrementLengthIntervalId = setInterval(this.incrementLength, 1000);
    } else {
      this.exercises = [];
    }
  }

  setExercisesForm() {
    const exercisesFormArray = this.form.get('exercises') as FormArray;
    this.exercises.forEach(exercise => {
      const exerciseGroup = this.fb.group({
        exerciseId: [exercise.id],
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
      clearInterval(this.incrementLengthIntervalId);
      this.form.controls['length'].setValue(this.convertToHMS(this.length));
      this.workoutLogService.postUserWorkoutLog(this.form.value).subscribe({
        next: () => {
          this.router.navigate(["/dashboard"]);
        },
        error: err => {
          this.errorMessage = err.error.errorMessage;
        }
      })
    }
  }

  incrementLength = () => {
    this.length++;
  }

  convertToHMS(s: number) {
    let hours = Math.floor(s / 3600);
    let minutes = Math.floor(s % 3600 / 60);
    let seconds = Math.floor(s % 3600 % 60);

    let hDisplay = hours > 0 ? (hours < 10 ? "0" + hours : hours) : "00";
    let mDisplay = minutes > 0 ? (minutes < 10 ? "0" + minutes : minutes) : "00";
    let sDisplay = seconds > 0 ? (seconds < 10 ? "0" + seconds : seconds) : "00";
    
    return hDisplay + ":" + mDisplay + ":" + sDisplay;    
  }

  ngOnDestroy(): void {
    if (this.incrementLengthIntervalId) {
      clearInterval(this.incrementLengthIntervalId);
    }
  }
}
