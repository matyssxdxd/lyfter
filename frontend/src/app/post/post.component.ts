import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PostService } from '../_services/post.service';
import { CommonModule } from '@angular/common';
import { MobileNavigationComponent } from '../mobile-navigation/mobile-navigation.component';
import { DesktopNavigationComponent } from '../desktop-navigation/desktop-navigation.component';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { StorageService } from '../_services/storage.service';
import { PostCommentService } from '../_services/post-comment.service';

@Component({
  selector: 'app-post',
  standalone: true,
  imports: [CommonModule, MobileNavigationComponent, DesktopNavigationComponent, ReactiveFormsModule],
  templateUrl: './post.component.html',
  styleUrl: './post.component.css'
})
export class PostComponent {
  isLoggedIn: boolean = false;
  post: any;
  errorMessage = "";
  form: FormGroup;

  constructor(private route: ActivatedRoute, private router: Router, private postService: PostService, private fb: FormBuilder, private storageService: StorageService, private postCommentService: PostCommentService) {
    this.isLoggedIn = storageService.isLoggedIn();

    if (!this.isLoggedIn) {
      this.router.navigate(['/login']);
    }

    const postId = parseInt(this.route.snapshot.params['id'], 10);

    this.postService.getById(postId).subscribe({
      next: data => {
        this.post = data;
        console.log(data);
      },
      error: err => {
        this.errorMessage = err.error.errorMessage;
      }
    })

    this.form = this.fb.group({
      userId: this.fb.control(this.storageService.getUser().id),
      postId: this.fb.control(postId),
      body: this.fb.control("", Validators.required)
    });
  }

  addComment() {
    if (this.form.valid) {
      const {body, userId, postId} = this.form.value;
      this.postCommentService.addComment(body, userId, postId).subscribe({
        next: data => {
          console.log(data);
          window.location.reload();
        },
        error: err => {
          this.errorMessage = err.error.errorMessage;
          console.log(this.errorMessage);
        }
      })
    }
  }

  convertDate(date: string) {
    return (new Date(date)).toLocaleDateString();
  }
}
