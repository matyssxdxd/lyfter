import { Exercise } from "./exercise"
import { Workout } from "./workout"

export interface WorkoutLogGet {
    id: number,
    date: string,
    length: string,
    exerciseSets: [{
        id: number,
        reps: number,
        weight: number,
        exercise: Exercise,
    
    }],
    workout: Workout
}
