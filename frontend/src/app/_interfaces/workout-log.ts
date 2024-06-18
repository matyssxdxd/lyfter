export interface WorkoutLog {
    workoutId: number,
    userId: number,
    exercises: [{
        exerciseId: number,
        sets: [{
            reps: number,
            weight: number
        }]
    }]
    length: string
}
