import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { AuthService } from '../_services/auth.service';
import { StorageService } from '../_services/storage.service';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  form!: FormGroup;
  isLoggedIn: boolean = false;
  isLoginFailed = false;
  errorMessage = "";

  constructor(
    private authService: AuthService,
    private storageService: StorageService,
    private router: Router
  ) {
    this.isLoggedIn = this.storageService.isLoggedIn();

    if (this.isLoggedIn) {
      this.router.navigate(["/dashboard"]);
    }
  }

  ngOnInit(): void {
    this.form = new FormGroup({
      username: new FormControl(null, [
        Validators.required
      ]),
      password: new FormControl(null, [
        Validators.required,
      ])
    })

    this.form.get('username')!.valueChanges.subscribe(() => {
      this.isLoginFailed = false;
    });

    this.form.get('password')!.valueChanges.subscribe(() => {
      this.isLoginFailed = false;
    });
  }

  get username() {
    return this.form.get("username");
  }

  get password() {
    return this.form.get("password");
  }

  onSubmit(): void {
    if (this.form.valid) {
      const { username, password } = this.form.value;

      this.authService.login(username, password).subscribe({
        next: data => {
          this.storageService.saveUser(data);
  
          this.isLoginFailed = false;
          this.isLoggedIn = true;
          this.router.navigate(['/dashboard']);
        },
        error: err => {
          this.errorMessage = "Incorrect username or password";
          this.isLoginFailed = true;
        }
      });
    }
  }

  reloadPage(): void {
    window.location.reload();
  }
}
