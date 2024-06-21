import { Routes } from '@angular/router';
import { AddPostComponent } from './components/add-post/add-post.component';
import { authGuard } from 'src/app/core/guards/auth.guard';
import { PostListComponent } from './components/post-list/post-list.component';
import { PostDetailComponent } from './components/post-detail/post-detail.component';


export const POSTS_ROUTES: Routes = [
  { path: '', component: PostListComponent, canActivate:[authGuard] }, 
     { path:'create', component: AddPostComponent, canActivate: [authGuard] },
     { path:':index', component: PostDetailComponent, canActivate: [authGuard] },
];
