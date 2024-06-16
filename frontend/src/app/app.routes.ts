import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ProfileComponent } from './profile/profile.component';
import { BoardUserComponent } from './board-user/board-user.component';
import { BoardAdminComponent } from './board-admin/board-admin.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { LogComponent } from './log/log.component';

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
        path: "user",
        component: BoardUserComponent
    },
    {
        path: "admin",
        component: BoardAdminComponent
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
        path: "",
        redirectTo: "home",
        pathMatch: "full"
    }
];
