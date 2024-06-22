import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ProfileComponent } from './profile/profile.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { LogComponent } from './log/log.component';
import { NewWorkoutComponent } from './new-workout/new-workout.component';

export const routes: Routes = [
    {
        path: "home",
        component: HomeComponent
    },
    {
        path: "login",
        component: LoginComponent
    },
    {
        path: "register",
        component: RegisterComponent
    },
    {
        path: "profile",
        component: ProfileComponent
    },
    {
        path: "dashboard",
        component: DashboardComponent
    },
    {
        path: "log",
        component: LogComponent
    },
    {
        path: "new-workout",
        component: NewWorkoutComponent
    },
    {
        path: "",
        redirectTo: "home",
        pathMatch: "full"
    }
];
