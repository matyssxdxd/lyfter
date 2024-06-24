import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const API_URL = "http://localhost:8080/api/post/";

const httpOptions = {
  headers: new HttpHeaders({ "Content-Type": "application/json" }),
  withCredentials: true
};

@Injectable({
  providedIn: 'root'
})
export class PostService {
  constructor(private http: HttpClient) {}

  getAll(): Observable<any> {
    return this.http.get(
      API_URL + "all",
      httpOptions
    );
  }

  getById(postId: number): Observable<any> {
    return this.http.get(
      API_URL + "find?id=" + postId,
      httpOptions
    );
  }

  newPost(body: string, userId: number): Observable<any> {
    return this.http.post(
      API_URL + "add",
      {
        body,
        userId
      },
      httpOptions
    );
  }
  
  deletePost(postId: number): Observable<any> {
    return this.http.delete(
      API_URL + "delete?id=" + postId,
      httpOptions
    );
  }

}
