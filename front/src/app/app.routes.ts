import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';

/**
 * App component will load HomeComponent as first component display
 * Then will get the paths defined in HOME_ROUTES
 * 
 */  
export const routes: Routes = [
    { path: '', pathMatch: 'full', redirectTo: ''},
    { path: '', component: HomeComponent }, 
    { path: '', loadChildren: () => import('./pages/home/home.routes').then(r => r.HOME_ROUTES)},
    //user is redirect to homeComponent in case of bad url
    { path: '**', component: HomeComponent },
  ]
;
