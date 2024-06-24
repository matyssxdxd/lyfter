import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MobileNavigationComponent } from '../mobile-navigation/mobile-navigation.component';
import { DesktopNavigationComponent } from '../desktop-navigation/desktop-navigation.component';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { StorageService } from '../_services/storage.service';
import { Router } from '@angular/router';
import { PostService } from '../_services/post.service';

@Component({
  selector: 'app-new-post',
  standalone: true,
  imports: [CommonModule, MobileNavigationComponent, DesktopNavigationComponent, ReactiveFormsModule],
  templateUrl: './new-post.component.html',
  styleUrl: './new-post.component.css'
})
export class NewPostComponent {
  isLoggedIn: boolean = false;
  form: FormGroup;
  errorMessage = "";

  constructor(private storageService: StorageService, private router: Router, private postService: PostService, private fb: FormBuilder) {
    this.isLoggedIn = this.storageService.isLoggedIn();

    if (!this.isLoggedIn) {
      this.router.navigate(['/login']);
    }

    this.form = this.fb.group({
      userId: this.fb.control(this.storageService.getUser().id),
      body: this.fb.control("", Validators.required)
    })
  }

  savePost() {
    if (this.form.valid) {
      const {body, userId} = this.form.value;
      this.postService.newPost(body, userId).subscribe({
        next: data => {
          console.log(data);
          this.router.navigate(['/posts']);
        },
        error: err => {
          this.errorMessage = err.error.errorMessage;
          console.log(this.errorMessage);
        }
      })
    }
  }
}
