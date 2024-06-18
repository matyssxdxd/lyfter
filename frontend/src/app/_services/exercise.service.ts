import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Exercise } from '../_interfaces/exercise';

const API_URL = "http://localhost:8080/api/exercise/";

const httpOptions = {
  headers: new HttpHeaders({ "Content-Type": "application/json" }),
  withCredentials: true
};

@Injectable({
  providedIn: 'root'
})
export class ExerciseService {
  constructor(public http: HttpClient) {}

  getAllExercises(): Observable<any> {
    return this.http.get<Exercise[]>(API_URL + "all", httpOptions);
  }
}
