import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { WorkoutLog } from '../_interfaces/workout-log';
import { Observable } from 'rxjs';

const API_URL = "http://localhost:8080/api/workout-log/";

const httpOptions = {
  headers: new HttpHeaders({ "Content-Type": "application/json" }),
  withCredentials: true
};


@Injectable({
  providedIn: 'root'
})
export class WorkoutLogService {
  constructor(private http: HttpClient) {}

  postUserWorkoutLog(workoutLog: WorkoutLog): Observable<any> {
    const { workoutId, userId, exercises, length } = workoutLog;
    return this.http.post(
      API_URL + "add",
      {
        workoutId,
        userId,
        exercises,
        length
      },
      httpOptions
    );
  }
}
