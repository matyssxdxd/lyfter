import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MobileNavigationComponent } from '../mobile-navigation/mobile-navigation.component';
import { DesktopNavigationComponent } from '../desktop-navigation/desktop-navigation.component';
import { StorageService } from '../_services/storage.service';
import { Router, RouterModule } from '@angular/router';
import { PostService } from '../_services/post.service';

@Component({
  selector: 'app-posts',
  standalone: true,
  imports: [CommonModule, MobileNavigationComponent, DesktopNavigationComponent, RouterModule],
  templateUrl: './posts.component.html',
  styleUrl: './posts.component.css'
})
export class PostsComponent {
  isLoggedIn: boolean = false;
  posts: any = [];
  errorMessage = "";

  constructor(private storageService: StorageService, private router: Router, private postService: PostService) {
    this.isLoggedIn = storageService.isLoggedIn();

    if (!this.isLoggedIn) {
      this.router.navigate(['/login']);
    }
  }

  ngOnInit(): void {
    this.postService.getAll().subscribe({
      next: data => {
        this.posts = data;
        console.log(data);
      },
      error: err => {
        this.errorMessage = err.error.errorMessage;
      }
    })
    
  }

  converDate(date: string) {
    return (new Date(date)).toLocaleDateString();
  }
}
