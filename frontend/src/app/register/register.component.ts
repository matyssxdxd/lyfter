import { Component } from '@angular/core';
import { AuthService } from '../_services/auth.service';
import { CommonModule } from '@angular/common';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, MatIconModule, RouterModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  form!: FormGroup;

  isSuccessful = false;
  isSignupFailed = false;
  errorMessage = "";

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.form = new FormGroup({
      username: new FormControl(null, [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(20)
      ]),
      email: new FormControl(null, [
        Validators.required,
        Validators.email
      ]),
      password: new FormControl(null, [
        Validators.required,
        Validators.minLength(6)
      ])
    });
  }

  get username() {
    return this.form.get("username");
  }

  get email() {
    return this.form.get("email");
  }

  get password() {
    return this.form.get("password");
  }

  onSubmit(): void {
    if (this.form.valid) {
      const { username, email, password } = this.form.value;

      this.authService.register(username, email, password).subscribe({
        next: data => {
          console.log(data);
          this.isSuccessful = true;
          this.isSignupFailed = false;
          this.router.navigate(['/home']);
        },
        error: err => {
          this.errorMessage = err.error.errorMessage;
          this.isSignupFailed = true;
        }
      });
    }
  }
}
