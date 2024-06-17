import { MuscleGroup } from "./muscle-group";

export interface Exercise {
    id: number,
    name: string,
    description: string,
    muscleGroups: MuscleGroup[]
}
