import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const API_URL = "http://localhost:8080/api/test/";

const httpOptions = {
  headers: new HttpHeaders({ "Content-Type": "application/json" }),
  withCredentials: true
};

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private http: HttpClient) {}

  getPublicContent(): Observable<any> {
    return this.http.get(API_URL + "all", { responseType: "text", withCredentials: true });
  }

  getUserBoard(): Observable<any> {
    return this.http.get(API_URL + "user", { responseType: "text", withCredentials: true });
  }

  getAdminBoard(): Observable<any> {
    return this.http.get(API_URL + "admin", { responseType: "text", withCredentials: true })
  }
}
