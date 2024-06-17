import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { StorageService } from './storage.service';
import { Workout } from '../_interfaces/workout';

const API_URL = "http://localhost:8080/api/workout/";

const httpOptions = {
  headers: new HttpHeaders({ "Content-Type": "application/json" }),
  withCredentials: true
};

@Injectable({
  providedIn: 'root'
})
export class WorkoutService {
  constructor(private http: HttpClient, private storageService: StorageService) {}

  getUserWorkouts(userId: number) {
    return this.http.get<Workout[]>(API_URL + "find?userId=" + userId, httpOptions);
  }
}
