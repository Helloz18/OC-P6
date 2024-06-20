import { Routes } from '@angular/router';
import { RegisterComponent } from '../pages/auth/components/register/register.component';
import { LoginComponent } from '../pages/auth/components/login/login.component';
import { ShellComponent } from './shell/shell.component';
import { PostListComponent } from '../pages/posts/components/post-list/post-list.component';
import { ProfileComponent } from '../pages/userProfile/components/profile/profile.component';
import { TopicListComponent } from '../pages/topics/components/topic-list/topic-list.component';
import { authGuard } from './guards/auth.guard';
import { AddPostComponent } from '../pages/posts/components/add-post/add-post.component';

/**
 * ShellComponent will contain the header that will be displayed on every pages of the application
 * That's why routes such as login have to be in children of ShellComponent
 * The toolbar of the header will display some information in case of the user is connected
 */
export const CORE_ROUTES: Routes = [
  { path: '', component: ShellComponent, 
    children: [
      { path: 'login', component: LoginComponent },
      { path: 'register', component: RegisterComponent },
      { path: 'topics', component: TopicListComponent, canActivate: [authGuard] },
      { path: 'posts',  loadChildren: () => import('../pages/posts/posts.routes').then(r => r.POSTS_ROUTES)},
      { path: 'profile', component: ProfileComponent, canActivate: [authGuard] }
    ],
  },

 
];
