import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ProfileComponent } from './profile/profile.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { LogComponent } from './log/log.component';
import { NewWorkoutComponent } from './new-workout/new-workout.component';
import { PostsComponent } from './posts/posts.component';
import { PostComponent } from './post/post.component';
import { NewPostComponent } from './new-post/new-post.component';

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
        path: "posts",
        component: PostsComponent
    },
    {
        path: "posts/:id",
        component: PostComponent
    },
    {
        path: "new-post",
        component: NewPostComponent
    },
    {
        path: "",
        redirectTo: "home",
        pathMatch: "full"
    }
];
