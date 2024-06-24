import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const API_URL = "http://localhost:8080/api/post-comment/";

const httpOptions = {
  headers: new HttpHeaders({ "Content-Type": "application/json" }),
  withCredentials: true
};

@Injectable({
  providedIn: 'root'
})
export class PostCommentService {

  constructor(private http: HttpClient) {}

  getCommentByPostId(postId: number): Observable<any> {
    return this.http.get(
      API_URL + "/find?id=" + postId,
      httpOptions
    );
  }

  addComment(body: string, userId: number, postId: number): Observable<any> {
    return this.http.post(
      API_URL + "add",
      {
        body,
        userId,
        postId
      },
      httpOptions
    );
  }

  deleteComment(commentId: number): Observable<any> {
    return this.http.delete(
      API_URL + "delete?id=" + commentId,
      httpOptions
    )
  }
}
