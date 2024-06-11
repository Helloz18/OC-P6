import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { ShellComponent } from './core/shell/shell.component';

export const routes: Routes = [
    { path:'', pathMatch:'full', redirectTo: ''},
    { path: '', loadChildren: () => import('./pages/home/home.routes').then(r => r.HOME_ROUTES) },
    { path: 'main', component: ShellComponent},
    { path: 'topics', loadChildren: () => import('./pages/topic/topic.routes').then(r => r.TOPIC_ROUTES)},
    //user is redirect to homeComponent in case of bad url
    { path: '**', component: HomeComponent },

  ]
  
;
